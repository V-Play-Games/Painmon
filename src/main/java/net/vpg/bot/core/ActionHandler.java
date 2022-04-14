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
package net.vpg.bot.core;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.vpg.bot.action.Sender;
import net.vpg.bot.entities.Area;
import net.vpg.bot.entities.Player;
import net.vpg.bot.entities.Route;
import net.vpg.bot.event.BotButtonEvent;
import net.vpg.bot.pokemon.Gender;
import net.vpg.bot.pokemon.Spawn;
import net.vpg.bot.ratelimit.AbstractRatelimiter;
import net.vpg.bot.ratelimit.Ratelimit;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface ActionHandler {
    Map<String, ActionHandler> handlers = new HashMap<>();

    static void registerHandler(ActionHandler handler) {
        handlers.put(handler.getName(), handler);
    }

    static ActionHandler get(String id) {
        return handlers.get(id);
    }

    String getName();

    void handle(BotButtonEvent e, String arg);

    class GenderHandler implements ActionHandler {
        @Override
        public String getName() {
            return "gender";
        }

        @Override
        public void handle(BotButtonEvent e, String arg) {
            Player player = Player.get(e.getUser().getId());
            if (player.getGender().isGenderless()) {
                player.setGender(arg.equals("m") ? Gender.MALE : Gender.FEMALE);
            }
        }
    }

    class AreaHandler implements ActionHandler {
        @Override
        public String getName() {
            return "area";
        }

        @Override
        public void handle(BotButtonEvent e, String arg) {
            Area.get(arg).send(e, e.getUser());
        }
    }

    class StarterHandler implements ActionHandler {
        @Override
        public String getName() {
            return "starter";
        }

        @Override
        public void handle(BotButtonEvent e, String arg) {
        }
    }

    class PropertyHandler implements ActionHandler {
        @Override
        public String getName() {
            return "property";
        }

        @Override
        public void handle(BotButtonEvent e, String arg) {
            Player player = Player.get(e.getUser().getId());
            String[] args = arg.split(";");
            player.update(args[0], args[1]);
        }
    }

    class BagHandler implements ActionHandler {
        @Override
        public String getName() {
            return "bag";
        }

        @Override
        public void handle(BotButtonEvent e, String arg) {
            Player player = Player.get(e.getUser().getId());
            String[] args = arg.split(";");
            player.getBag().addItemCount(args[0], Integer.parseInt(args[1]));
        }
    }

    class EncounterHandler extends AbstractRatelimiter implements ActionHandler {
        public EncounterHandler() {
            super(30, TimeUnit.SECONDS);
        }

        @Override
        public String getName() {
            return "encounter";
        }

        @Override
        public void handle(BotButtonEvent e, String arg) {
            if (checkRatelimited(e.getIdLong(), e)) {
                return;
            }
            Spawn spawn = Route.get(arg).spawn();
            if (spawn != null && Range.random(0, 10) != 0) {
                e.replyEmbeds(
                    new EmbedBuilder()
                        .setTitle("A wild " + spawn.getEffectiveName() + " appeared!")
                        .setDescription("Click the buttons to take an action!")
                        .build()
                ).addActionRow(
                    Button.primary("battle:" + e.getUser().getId() + ":spawn:" + spawn.getId(), "Battle")
                ).queue();
            } else {
                e.reply("No wild pokemon in sight. Try again later!").queue();
            }
            ratelimit(e.getIdLong());
        }

        @Override
        public void onRatelimit(Sender e, Ratelimit limit) {
            e.send("You have to wait **" + limit.getCooldownString() + "** before encountering another Pokemon!").queue();
        }
    }
}
