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

import net.vpg.bot.entities.EntityReference;
import net.vpg.bot.entities.Move;
import net.vpg.bot.entities.PlayablePokemon;
import net.vpg.bot.entities.Route.WildPokemon;

import java.util.List;

public class Spawn extends PlayablePokemon {
    private final WildPokemon wild;

    public Spawn(WildPokemon pokemon) {
        super(pokemon.getPokemon(), "spawn" + System.nanoTime(), null);
        this.wild = pokemon;
        CACHE.put(id, this);
    }

    public static Spawn get(String id) {
        assert id.startsWith("spawn");
        return (Spawn) CACHE.get(id);
    }

    public WildPokemon asWildPokemon() {
        return wild;
    }

    @Override
    public void randomize() {
        if (getLevel() != 0) return;
        setLevel(wild.getLevelRange().random());
        List<EntityReference<Move>> possibleMoves = wild.getPossibleMoves();
        for (int i = 1, fence = wild.getMoveRange().random(); i <= fence; i++) {
            setMove(i, possibleMoves.get(i - 1).get());
        }
    }
}
