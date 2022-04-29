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
import net.vpg.bot.entities.Route.SpawnData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WildPokemon extends PokemonData {
    public static final Map<String, WildPokemon> CACHE = new HashMap<>();
    private final SpawnData spawnData;

    public WildPokemon(SpawnData spawnData) {
        super(spawnData.getPokemon(), "spawn" + System.nanoTime());
        this.spawnData = spawnData;
        CACHE.put(id, this);
    }

    public static WildPokemon get(String id) {
        assert id.startsWith("spawn");
        return CACHE.get(id);
    }

    public SpawnData getSpawnData() {
        return spawnData;
    }

    @Override
    public Type getType() {
        return Type.WILD;
    }

    @Override
    public void randomize() {
        super.randomize();
        setLevel(spawnData.getLevelRange().random());
        Moveset moveset = getMoveset();
        List<EntityReference<Move>> possibleMoves = spawnData.getPossibleMoves();
        for (int i = 1, fence = spawnData.getMoveRange().random(); i <= fence; i++) {
            moveset.setMove(i, possibleMoves.get(i - 1).get());
        }
    }
}
