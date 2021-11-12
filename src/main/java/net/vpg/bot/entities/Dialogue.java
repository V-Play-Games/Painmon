/*
 * Copyright 2021 Vaibhav Nargwani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.vpg.bot.entities;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.interactions.Interaction;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.core.ActionHandler;
import net.vpg.bot.core.Condition;
import net.vpg.bot.core.Player;
import net.vpg.bot.framework.Util;
import net.vpg.bot.framework.commands.CommandReceivedEvent;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Dialogue implements Entity {
    public static final Map<String, Dialogue> CACHE = new HashMap<>();
    private String id;
    private Map<String, State> states;

    public Dialogue(DataObject data) {
        this.id = data.getString("id");
        this.states = data.getArray("states")
            .stream(DataArray::getObject)
            .map(State::new)
            .collect(Util.groupingBy(State::getId));
    }

    public static EntityInfo<Dialogue> getInfo() {
        return new EntityInfo<>(Dialogue.class.getResource("dialogue.json"), Dialogue::new, CACHE);
    }

    public static Dialogue get(String id) {
        return CACHE.get(id);
    }

    public String getId() {
        return id;
    }

    public String getText(Player player) {
        return getStateFor(player).getText(player);
    }

    public void send(CommandReceivedEvent e) {
        getStateFor(e.getUser().getId()).send(e);
    }

    public void send(Interaction e) {
        getStateFor(e.getUser().getId()).send(e);
    }

    public void send(CommandReceivedEvent e, Player player) {
        getStateFor(player).send(e, player);
    }

    public void send(Interaction e, Player player) {
        getStateFor(player).send(e, player);
    }

    public State getState(String id) {
        return states.get(id);
    }

    public State getStateFor(String id) {
        return getStateFor(Player.get(id));
    }

    public State getStateFor(Player player) {
        return states.values()
            .stream()
            .sorted(Comparator.comparingInt(State::getPriority))
            .filter(state -> Condition.doesFulfill(state.conditions, player))
            .findFirst()
            .orElseThrow(RuntimeException::new);
    }

    public void executeActions(ButtonClickEvent e, String stateId, String actionId) {
        getState(stateId).executeActions(e, actionId);
    }

    @Nonnull
    @Override
    public DataObject toData() {
        return Entity.super.toData()
            .put("states", DataArray.empty().addAll(states.values()));
    }

    public class State implements Entity {
        private String id;
        private int priority;
        private List<String> conditions;
        private DataArray interaction_actions;
        private String text;
        private Map<String, List<String>> buttonActions;
        private DataArray rawButtons;
        private List<Button> buttons;

        public State(DataObject data) {
            this.id = data.getString("id");
            this.text = data.getString("text");
            this.priority = data.getInt("priority");
            this.conditions = List.of(data.getString("conditions").split(";;;"));
            this.interaction_actions = data.optArray("interaction-actions").orElseGet(DataArray::empty);
            this.buttonActions = new HashMap<>();
            this.rawButtons = data.getArray("buttons");
            this.buttons = this.rawButtons.stream(DataArray::getObject)
                .map(button -> {
                    String buttonId = button.getString("id");
                    buttonActions.put(buttonId, button.getArray("actions")
                        .stream(DataArray::getString)
                        .collect(Collectors.toList()));
                    if (button.hasKey("emote")) {
                        return Button.primary(buttonId, Emoji.fromMarkdown(button.getString("emote")));
                    } else {
                        return Button.primary(buttonId, button.getString("label"));
                    }
                })
                .collect(Collectors.toList());
        }

        public Dialogue getParent() {
            return Dialogue.this;
        }

        public String getId() {
            return id;
        }

        public String getText(Player player) {
            return player.resolveReferences(text);
        }

        public void send(CommandReceivedEvent e) {
            send(e, Player.get(e.getUser().getId()));
        }

        public void send(Interaction e) {
            send(e, Player.get(e.getUser().getId()));
        }

        public void send(CommandReceivedEvent e, Player player) {
            e.send(getText(player.setPosition(Dialogue.this.id)))
                .setActionRows(getActionRow(player.getId()))
                .queue();
        }

        public void send(Interaction e, Player player) {
            e.reply(getText(player.setPosition(Dialogue.this.id)))
                .addActionRows(getActionRow(player.getId()))
                .queue();
        }

        public List<String> getButtonActions(String actionId) {
            return buttonActions.get(actionId);
        }

        public void executeActions(ButtonClickEvent e, String actionId) {
            getButtonActions(actionId).forEach(s -> ActionHandler.get(Util.getMethod(s)).handle(e, Util.getArgs(s)));
            interaction_actions.stream(DataArray::getString).forEach(s -> ActionHandler.get(Util.getMethod(s)).handle(e, Util.getArgs(s)));
        }

        public ActionRow getActionRow(String userId) {
            return ActionRow.of(buttons.stream().map(b -> b.withId("area:" + Dialogue.this.id + ":" + this.id + ":" + b.getId() + ":" + userId)).collect(Collectors.toList()));
        }

        public List<String> getConditions() {
            return conditions;
        }

        public int getPriority() {
            return priority;
        }

        @Nonnull
        @Override
        public DataObject toData() {
            return Entity.super.toData()
                .put("priority", priority)
                .put("conditions", String.join(";;;", conditions))
                .put("interaction_actions", interaction_actions)
                .put("text", text)
                .put("buttonActions", buttonActions)
                .put("buttons", rawButtons);
        }
    }
}
