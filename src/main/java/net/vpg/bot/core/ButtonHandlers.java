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
import net.vpg.bot.commands.trilogy.ViewCommand;
import net.vpg.bot.entities.Area;
import net.vpg.bot.entities.Player;
import net.vpg.bot.event.BotButtonEvent;
import net.vpg.bot.pokemon.WildPokemon;
import net.vpg.bot.pokemon.battle.Battle;

import java.util.Map;

public interface ButtonHandlers {
    class AreaHandler implements ButtonHandler {
        @Override
        public String getName() {
            return "area";
        }

        @Override
        public void handle(BotButtonEvent e) {
            if (!e.getArg(3).equals(e.getUser().getId())) return;
            Area.get(e.getArg(0)).executeActions(e, e.getArg(1), e.getArg(2));
        }
    }

    class BagHandler implements ButtonHandler {
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
            BagCommand.execute(e, Player.get(user.getId()), page);
        }
    }

    class ViewHandler implements ButtonHandler {
        @Override
        public String getName() {
            return "view";
        }

        @Override
        public void handle(BotButtonEvent e) {
            ViewCommand.execute(e, null, e.getArg(0));
        }
    }

    class BattleHandler implements ButtonHandler {
        public static void executeBattle() {

        }

        @Override
        public String getName() {
            return "battle";
        }

        @Override
        public void handle(BotButtonEvent e) {
            String userId = e.getUser().getId();
            if (!e.getArg(0).equals(userId)) {
                return;
            }
            Player player = Player.get(userId);
            switch (e.getArg(1)) {
                case "trainer":
                    e.send("Trainer Battles not supported yet").queue();
                    break;
                case "spawn":
                    // battle:<user-id>:spawn:<spawn-id>
                    String spawnId = e.getArg(2);
                    WildPokemon spawn = WildPokemon.get(spawnId);
                    if (spawn == null) return;
                    WildPokemon.CACHE.remove(spawnId);
                    spawn.randomize();
                    battle = Battle.between(player.getTeam(), spawn);
                    Map<Battle.Position, String> positions = battle.getPositions();
                    // TODO: Send the battle
                default:
                    throw new IllegalArgumentException("Unsupported battle type");
            }
        }
    }
}
