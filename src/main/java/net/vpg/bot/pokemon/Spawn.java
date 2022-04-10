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
import net.vpg.bot.core.Util;
import net.vpg.bot.entities.Ability;
import net.vpg.bot.entities.Move;
import net.vpg.bot.entities.PlayablePokemon;
import net.vpg.bot.entities.Pokemon;
import net.vpg.bot.entities.Route.WildPokemon;

public class Spawn extends PlayablePokemon {
    private static final Range SHINY_RANGE = Range.of(0, 4096);
    private static final Range EV_RANGE = Range.of(0, 31);
    private final WildPokemon wild;

    public Spawn(String id, WildPokemon pokemon) {
        super(pokemon.getReference().get(), id, null);
        this.wild = pokemon;
    }

    public WildPokemon asWildPokemon() {
        return wild;
    }

    public void fillIn() {
        if (getLevel() != 0) return;
        setLevel(wild.getLevelRange().random());
        String[] possibleMoves = wild.getPossibleMoves();
        for (int i = 1, fence = wild.getMoveRange().random(); i <= fence; i++) {
            setMove(i, Move.get(possibleMoves[i - 1]));
        }
        setShiny(SHINY_RANGE.random() == 0);
        setNature(Util.getRandom(Nature.values()));
        for (Stat stat : Stat.values()) {
            setIv(stat, EV_RANGE.random());
        }
        Ability[] abilities = getPossibleAbilities()
            .stream()
            .filter(a -> !a.isHidden())
            .map(Pokemon.AbilitySlot::getAbility)
            .toArray(Ability[]::new);
        setAbility(Util.getRandom(abilities));
        setGender(getSpecies().getGenderRate().generate());
    }
}
