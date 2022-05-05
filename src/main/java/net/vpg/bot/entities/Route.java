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

import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.core.Range;
import net.vpg.bot.pokemon.WildPokemon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Route implements Entity {
    public static final Map<String, Route> CACHE = new HashMap<>();
    private static final Range SPAWN_RANGE = Range.of(1, 100);
    private final String id;
    private final SpawnData[] encounterList;

    public Route(DataObject data) {
        this.id = data.getString("id");
        this.encounterList = data.getArray("table")
            .stream(DataArray::getObject)
            .map(SpawnData::new)
            .toArray(SpawnData[]::new);
    }

    public static EntityInfo<Route> getInfo() {
        return new EntityInfo<>(Route.class.getResource("route.json"), Route::new, CACHE);
    }

    public static Route get(String id) {
        return CACHE.get(id);
    }

    @Override
    public String getId() {
        return id;
    }

    public SpawnData[] getEncounterList() {
        return encounterList;
    }

    public WildPokemon spawn() {
        int spawn = SPAWN_RANGE.random();
        SpawnData pokemon = null;
        for (SpawnData wildPokemon : encounterList) {
            spawn -= wildPokemon.getRate();
            if (spawn < 0) {
                pokemon = wildPokemon;
                break;
            }
        }
        return pokemon == null ? null : new WildPokemon(pokemon);
    }

    public class SpawnData {
        private final EntityReference<Pokemon> reference;
        private final int rate;
        private final Range levelRange;
        private final Range moveRange;
        private final List<EntityReference<Move>> possibleMoves;

        public SpawnData(DataObject data) {
            reference = new EntityReference<>(Pokemon.INFO, data.getString("id"));
            rate = data.getInt("rate");
            levelRange = Range.of(data.getString("level_range"));
            moveRange = Range.of(data.getString("move_range"));
            possibleMoves = data.getArray("moves_list")
                .stream(DataArray::getString)
                .map(move -> new EntityReference<>(Move.INFO, id))
                .collect(Collectors.toList());

            assert possibleMoves.size() >= moveRange.getUpper();
        }

        public Pokemon getPokemon() {
            return reference.get();
        }

        public int getRate() {
            return rate;
        }

        public Range getLevelRange() {
            return levelRange;
        }

        public Range getMoveRange() {
            return moveRange;
        }

        public List<EntityReference<Move>> getPossibleMoves() {
            return possibleMoves;
        }

        public Route getRoute() {
            return Route.this;
        }
    }
}
