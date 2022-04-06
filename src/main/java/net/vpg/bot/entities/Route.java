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
import net.vpg.bot.pokemon.Spawn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Route implements Entity {
    public static final Map<String, Route> CACHE = new HashMap<>();
    private static final Range range = Range.of(0, 100);
    private final String id;
    private final List<WildPokemon> encounterList;

    public Route(DataObject data) {
        this.id = data.getString("id");
        this.encounterList = data.getArray("table")
            .stream(DataArray::getObject)
            .map(WildPokemon::new)
            .collect(Collectors.toList());
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

    public List<WildPokemon> getEncounterList() {
        return encounterList;
    }

    public Spawn spawn() {
        int spawn = range.random();
        WildPokemon pokemon = null;
        for (WildPokemon wildPokemon : encounterList) {
            spawn -= wildPokemon.getRate();
            if (spawn < 0) {
                pokemon = wildPokemon;
                break;
            }
        }
        if (pokemon == null) return null;
        return new Spawn("spawn" + System.nanoTime(), pokemon);
    }

    public class WildPokemon {
        private final EntityReference<Pokemon> reference;
        private final int rate;
        private final Range levelRange;
        private final Range moveRange;
        private final String[] possibleMoves;

        public WildPokemon(DataObject data) {
            reference = new EntityReference<>(Pokemon.INFO, data.getString("id"));
            rate = data.getInt("rate");
            levelRange = Range.of(data.getString("level_range"));
            moveRange = Range.of(data.getString("move_count"));
            possibleMoves = data.getArray("moves_list").stream(DataArray::getString).toArray(String[]::new);

            assert possibleMoves.length >= moveRange.getUpper();
        }

        public EntityReference<Pokemon> getReference() {
            return reference;
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

        public String[] getPossibleMoves() {
            return possibleMoves;
        }

        public Route getRoute() {
            return Route.this;
        }
    }
}
