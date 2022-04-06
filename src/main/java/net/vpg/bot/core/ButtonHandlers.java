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

import net.dv8tion.jda.api.entities.User;
import net.vpg.bot.commands.trilogy.BagCommand;
import net.vpg.bot.entities.Dialogue;
import net.vpg.bot.event.BotButtonEvent;

public interface ButtonHandlers {
    class Area implements ButtonHandler {
        @Override
        public String getName() {
            return "area";
        }

        @Override
        public void handle(BotButtonEvent e) {
            if (!e.getArg(3).equals(e.getUser().getId())) return;
            Dialogue.get(e.getArg(0)).executeActions(e, e.getArg(1), e.getArg(2));
        }
    }

    class Bag implements ButtonHandler {
        @Override
        public String getName() {
            return "bag";
        }

        @Override
        public void handle(BotButtonEvent e) {
            User user = e.getUser();
            if (!e.getArg(0).equals(user.getId())) {
                return;
            }
            long page = Long.parseLong(e.getArg(1));
            BagCommand.execute(e, user, page);
        }
    }

    class Battle implements ButtonHandler {
        @Override
        public String getName() {
            return "battle";
        }

        @Override
        public void handle(BotButtonEvent e) {
            User user = e.getUser();
            if (!e.getArg(0).equals(user.getId())) {
                return;
            }
            switch (e.getArg(1)) {
                case "trainer":
                    e.send("Trainer Battles not supported yet").queue();
                    break;
                case "spawn":
                    // battle:user-id:spawn:route:pokemon
                    String route = e.getArg(2);
                    String pokemon = e.getArg(3);
            }
            e.send("Insert Battle").queue();
        }
    }

    class Catch implements ButtonHandler {
        @Override
        public String getName() {
            return "catch";
        }

        @Override
        public void handle(BotButtonEvent e) {
            User user = e.getUser();
            if (!e.getArg(0).equals(user.getId())) {
                return;
            }
            e.send("Insert Catch, tutuk, tutuk, tutuk, TADA CAUGHT!").queue();
        }
    }
}
