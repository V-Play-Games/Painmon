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

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.vpg.bot.entities.Dialogue;

import java.util.HashMap;
import java.util.Map;

public interface ActionHandler {
    Map<String, ActionHandler> handlers = new HashMap<>();

    String getName();

    void handle(ButtonClickEvent e, String arg);

    static void registerHandler(ActionHandler handler) {
        handlers.put(handler.getName(), handler);
    }

    static ActionHandler get(String id) {
        return handlers.get(id);
    }

    class Gender implements ActionHandler {
        @Override
        public String getName() {
            return "gender";
        }

        @Override
        public void handle(ButtonClickEvent e, String arg) {
            Player player = Player.get(e.getUser().getId());
            if (player.getMale() == -1) {
                player.setMale(arg.equals("m") ? 1 : 0);
            }
        }
    }

    class Area implements ActionHandler {
        @Override
        public String getName() {
            return "area";
        }

        @Override
        public void handle(ButtonClickEvent e, String arg) {
            Dialogue.get(arg).send(e);
        }
    }

    class Starter implements ActionHandler {
        @Override
        public String getName() {
            return "starter";
        }

        @Override
        public void handle(ButtonClickEvent e, String arg) {
        }
    }

    class Property implements ActionHandler {
        @Override
        public String getName() {
            return "property";
        }

        @Override
        public void handle(ButtonClickEvent e, String arg) {
            Player player = Player.get(e.getUser().getId());
            String[] args = arg.split(";");
            player.update(args[0], args[1]);
        }
    }
}
