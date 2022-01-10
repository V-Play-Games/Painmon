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

import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.framework.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Route implements Entity {
    public static final Map<String, Route> CACHE = new HashMap<>();
    private final String id;
    private final Map<EntityReference<Pokemon>, Integer> rawRates;
    private Map<Pokemon, Integer> resolvedRates;
    private Pokemon[] resolvedSpawns;

    public Route(DataObject data) {
        this.id = data.getString("id");
        rawRates = data.getObject("rates")
            .toMap()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(e -> new EntityReference<>(Pokemon.INFO, e.getKey()), e -> (Integer) e.getValue()));
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

    public Map<Pokemon, Integer> getRates() {
        if (resolvedRates == null) {
            resolvedRates = rawRates.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey().get(), Map.Entry::getValue));
        }
        return resolvedRates;
    }

    public Pokemon spawn() {
        if (resolvedSpawns == null) {
            resolvedSpawns = getRates()
                .entrySet()
                .stream()
                .map(entry -> {
                    Pokemon[] spawns = new Pokemon[entry.getValue()];
                    Arrays.fill(spawns, entry.getKey());
                    return spawns;
                })
                .flatMap(Arrays::stream)
                .toArray(Pokemon[]::new);
        }
        return Util.getRandom(resolvedSpawns);
    }
}
