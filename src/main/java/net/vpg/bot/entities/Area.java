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
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.action.Sender;
import net.vpg.bot.core.ActionHandler;
import net.vpg.bot.core.Condition;
import net.vpg.bot.core.Util;
import net.vpg.bot.event.BotButtonEvent;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

public class Area implements Entity {
    public static final Map<String, Area> CACHE = new HashMap<>();
    public static final EntityInfo<Area> INFO = new EntityInfo<>(Area.class.getResource("area.json"), Area::new, CACHE);
    private final DataObject data;
    private final String id;
    private final Map<String, State> states;

    public Area(DataObject data) {
        this.data = data;
        this.id = data.getString("id");
        this.states = data.getArray("states")
            .stream(DataArray::getObject)
            .map(State::new)
            .collect(Util.groupingBy(State::getId));
    }

    public static Area get(String id) {
        return CACHE.get(id);
    }

    public String getId() {
        return id;
    }

    public String getText(Player player) {
        return getStateFor(player).getText(player);
    }

    public void send(Sender e, User user) {
        getStateFor(user.getId()).send(e, user);
    }

    public void send(Sender e, Player player) {
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

    public void executeActions(BotButtonEvent e, String stateId, String actionId) {
        getState(stateId).executeActions(e, actionId);
    }

    @Nonnull
    @Override
    public DataObject toData() {
        return data;
    }

    public class State implements Entity {
        private final DataObject data;
        private final String id;
        private final int priority;
        private final List<String> conditions;
        private final List<String> defaultActions;
        private final String text;
        private final Map<String, List<String>> buttonActions;
        private final List<Button> buttons;

        public State(DataObject data) {
            this.data = data;
            this.id = data.getString("id");
            this.text = data.getString("text");
            this.priority = data.getInt("priority");
            this.conditions = List.of(data.getString("conditions").split(";;;"));
            this.defaultActions = data.optArray("default-actions")
                .orElseGet(DataArray::empty)
                .stream(DataArray::getString)
                .collect(Collectors.toList());
            this.buttonActions = new HashMap<>();
            this.buttons = new ArrayList<>();
            data.getArray("buttons").stream(DataArray::getObject).map(button -> {
                String buttonId = button.getString("id");
                buttonActions.put(buttonId, button.getArray("actions")
                    .stream(DataArray::getString)
                    .collect(Collectors.toList()));
                if (button.hasKey("emote")) {
                    return Button.primary(buttonId, Emoji.fromMarkdown(button.getString("emote")));
                } else {
                    return Button.primary(buttonId, button.getString("label"));
                }
            }).forEach(buttons::add);
        }

        public Area getParent() {
            return Area.this;
        }

        public String getId() {
            return id;
        }

        public String getText(Player player) {
            return player.resolveReferences(text);
        }

        public void send(Sender e, User user) {
            send(e, Player.get(user.getId()));
        }

        public void send(Sender e, Player player) {
            player.setPosition(Area.this.id);
            e.send(getText(player))
                .setActionRows(getActionRow(player.getId()))
                .queue();
        }

        public List<String> getButtonActions(String actionId) {
            return buttonActions.get(actionId);
        }

        public void executeActions(BotButtonEvent e, String actionId) {
            Player player = Player.get(e.getUser().getId());
            Area area = Area.get(player.getPosition());
            State state = area.getStateFor(player);
            if (area != Area.this && state != this) return;
            getButtonActions(actionId).forEach(s -> ActionHandler.handleRaw(e, player, s));
            defaultActions.forEach(s -> ActionHandler.handleRaw(e, player, s));
        }

        public ActionRow getActionRow(String userId) {
            return ActionRow.of(buttons.stream().map(b -> b.withId("area:" + Area.this.id + ":" + this.id + ":" + b.getId() + ":" + userId)).collect(Collectors.toList()));
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
            return data;
        }
    }
}
