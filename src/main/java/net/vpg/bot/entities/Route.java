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

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Route implements Entity {
    public static final Map<String, Route> CACHE = new HashMap<>();
    private final String id;
    private final Map<Pokemon, Integer> rates = new HashMap<>();

    public Route(DataObject data) {
        this.id = data.getString("id");
        rates.putAll(data.getObject("rates")
            .toMap()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(e -> Pokemon.get(e.getKey()), e -> (Integer) e.getValue())));
    }

    public static EntityInfo<Route> getInfo() {
        return new EntityInfo<Route>(Route.class.getResource("route.json"), Route::new, CACHE);
    }

    @Override
    public String getId() {
        return id;
    }

    public Map<Pokemon, Integer> getRates() {
        return rates;
    }
}
