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
package net.vpg.bot.pokemon.team;

import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.entities.Entity;
import net.vpg.bot.pokemon.TrainerPokemon;

import java.util.HashMap;
import java.util.Map;

public class NPCTeam extends PokemonTeam<TrainerPokemon> implements Entity {
    public static final Map<String, NPCTeam> CACHE = new HashMap<>();
    private final String id;

    public NPCTeam(DataObject data) {
        super(data.getArray("team")
            .stream(DataArray::getString)
            .map(TrainerPokemon::get)
            .toArray(TrainerPokemon[]::new));
        this.id = data.getString("id");
    }

    public static NPCTeam get(String id) {
        return CACHE.get(id);
    }

    @Override
    public String getId() {
        return id;
    }
}
