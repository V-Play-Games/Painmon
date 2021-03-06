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
package net.vpg.bot.pokemon;

import net.vpg.bot.core.Range;
import net.vpg.bot.entities.Item;
import net.vpg.bot.entities.Pokemon;
import net.vpg.bot.pokemon.battle.BattlePokemon;

public class Capture {
    private static final Range SHAKE_RANGE = Range.of(0, 255);

    public static Message getShakeMessage(int selfLevel, int turn, BattlePokemon pokemon, Item ball, Method method) {
        if (ball.getId().equals("master-ball"))
            return Message.SHAKE_4;
        double hpModifier = 1 - (2.0 * pokemon.getCurrentHP()) / (3 * pokemon.getMaxHP());
        Pokemon basePokemon = pokemon.getPokemonData().getBase();
        int baseRate = basePokemon.getSpecies().getCaptureRate();
        int level = pokemon.getPokemonData().getLevel();
        double levelModifier = level > 20 ? 1 : (30 - level) / 10.0;
        double rate = baseRate * hpModifier * levelModifier;
        switch (pokemon.getStatus()) {
            case SLEEP:
            case FREEZE:
                rate *= 2.5;
                break;
            case PARALYSIS:
            case POISON:
            case TOXIC_POISON:
            case BURN:
                rate *= 1.5;
                break;
        }
        switch (ball.getId()) {
            case "master-ball": // shouldn't happen but putting here just in case
            case "beast-ball": // special handling later on
            case "poke-ball":
            case "premier-ball":
            case "luxury-ball":
                break;
            case "great-ball":
                rate *= 1.5;
                break;
            case "ultra-ball":
                rate *= 2;
                break;
            case "level-ball":
                if (selfLevel > 4 * level)
                    rate *= 8;
                else if (selfLevel > 2 * level)
                    rate *= 4;
                else if (selfLevel > level)
                    rate *= 2;
                break;
            case "lure-ball":
                if (method == Method.FISH)
                    rate *= 4;
                break;
            case "heavy-ball":
                double weight = basePokemon.getWeight();
                if (weight > 300)
                    rate += 30;
                else if (weight > 200)
                    rate += 20;
                else if (weight < 100)
                    rate -= 20;
                break;
            case "net-ball":
                String type = basePokemon.getType().getName();
                if (type.contains("bug") || type.contains("water"))
                    rate *= 3.5;
                break;
            case "nest-ball":
                if (level < 30)
                    rate *= (41 - level) / 10.0;
                break;
            case "repeat-ball":
                // TODO: Figure out how to implement this (rate*3.5 if species is already caught)
                break;
            case "timer-ball":
                rate *= Math.max(4, (1.0 + turn) * 1229 / 4096);
                break;
            case "dive-ball":
                if (method == Method.SURF || method == Method.FISH || method == Method.DIVE)
                    rate *= 3.5;
                break;
            case "dusk-ball":
                if (method == Method.CAVE)
                    rate *= 3.5;
                break;
            case "quick-ball":
                if (turn == 1)
                    rate *= 5;
                break;
            default:
                throw new IllegalStateException(ball + " is not supported as a poke ball.");
        }
        boolean ub = basePokemon.getAbilities().stream().map(Pokemon.AbilitySlot::getId).anyMatch(id -> id.equals("beast-boost"));
        boolean beastBall = ball.getId().equals("beast-ball");
        rate *= ub && beastBall ? 5 : ub || beastBall ? 0.1 : 1;
        rate = Math.max((int) rate, 1);
        // TODO: Figure out about critical captures
        int shakeThreshold = (int) (65536 / Math.pow(255 / rate, 0.1875));
        int i = 0;
        while (i < 4 && SHAKE_RANGE.random() < shakeThreshold) i++;
        return Message.values()[i];
    }

    public enum Message {
        SHAKE_0("Oh yikes! The Pokemon broke free!", false),
        SHAKE_1("Darn it! It appeared to be caught!", false),
        SHAKE_2("Argh! So close!", false),
        SHAKE_3("Gah! Come on! You've gotta be kidding me!", false),
        SHAKE_4("Woohoo! Gotcha! %s was caught!", true);

        private final String message;
        private final boolean caught;

        Message(String message, boolean caught) {
            this.message = message;
            this.caught = caught;
        }

        public String getMessage() {
            return message;
        }

        public boolean isCaught() {
            return caught;
        }
    }

    public enum Method {
        LAND,
        CAVE,
        SURF,
        DIVE,
        FISH
    }
}
