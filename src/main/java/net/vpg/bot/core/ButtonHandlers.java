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

import net.vpg.bot.entities.Dialogue;
import net.vpg.bot.framework.BotButtonEvent;
import net.vpg.bot.framework.ButtonHandler;

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

    class Route implements ButtonHandler {
        @Override
        public String getName() {
            return "route";
        }

        @Override
        public void handle(BotButtonEvent e) {
            if (!e.getArg(3).equals(e.getUser().getId())) return;
            Dialogue.get(e.getArg(0)).executeActions(e, e.getArg(1), e.getArg(2));
        }
    }
}
