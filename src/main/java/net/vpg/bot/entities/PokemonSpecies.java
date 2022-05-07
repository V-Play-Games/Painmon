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
import net.vpg.bot.pokemon.GenderRate;
import net.vpg.bot.pokemon.GrowthRate;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PokemonSpecies implements Entity {
    public static final Map<String, PokemonSpecies> CACHE = new HashMap<>();
    public static final EntityInfo<PokemonSpecies> INFO = new EntityInfo<>(PokemonSpecies.class.getResource("pokemon-species.json"), PokemonSpecies::new, CACHE);
    private final String id;
    private final int generation;
    private final int evolutionChain;
    private final int baseHappiness;
    private final int captureRate;
    private final GrowthRate growthRate;
    private final GenderRate genderRate;
    private final List<String> eggGroups;
    private final List<String> tags;

    public PokemonSpecies(DataObject data) {
        this.id = data.getString("name");
        this.generation = data.getInt("generation");
        this.evolutionChain = data.getInt("evolution_chain");
        this.baseHappiness = data.getInt("base_happiness");
        this.captureRate = data.getInt("capture_rate");
        this.growthRate = GrowthRate.fromId(data.getString("growth_rate"));
        this.genderRate = GenderRate.of(data.getInt("gender_rate"));
        this.eggGroups = data.getArray("egg_groups").stream(DataArray::getString).collect(Collectors.toList());
        this.tags = data.getArray("tags").stream(DataArray::getString).collect(Collectors.toList());
    }

    @Override
    public String getId() {
        return id;
    }

    public int getGeneration() {
        return generation;
    }

    public int getEvolutionChain() {
        return evolutionChain;
    }

    public int getBaseHappiness() {
        return baseHappiness;
    }

    public int getCaptureRate() {
        return captureRate;
    }

    public GrowthRate getGrowthRate() {
        return growthRate;
    }

    public GenderRate getGenderRate() {
        return genderRate;
    }

    public List<String> getEggGroups() {
        return eggGroups;
    }

    public List<String> getTags() {
        return tags;
    }

    @Nonnull
    @Override
    public DataObject toData() {
        return DataObject.empty()
            .put("id", id)
            .put("generation", generation)
            .put("evolutionChain", evolutionChain)
            .put("baseHappiness", baseHappiness)
            .put("captureRate", captureRate)
            .put("growthRate", growthRate.getId())
            .put("genderRate", genderRate.getFemaleRate())
            .put("eggGroups", eggGroups)
            .put("tags", tags);
    }
}
