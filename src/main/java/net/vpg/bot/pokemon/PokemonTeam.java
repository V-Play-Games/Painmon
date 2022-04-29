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

import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.SerializableArray;

import javax.annotation.Nonnull;

public class PokemonTeam implements SerializableArray {
    private final DataArray data;
    private final TrainerPokemon[] team = new TrainerPokemon[6];

    public PokemonTeam(DataArray data) {
        this.data = data;
        for (int i = 0, size = data.toList().size(); i < size; i++) {
            team[i] = PlayerPokemon.get(data.getString(i));
        }
    }

    public PokemonTeam() {
        this.data = DataArray.empty();
    }

    public TrainerPokemon getPokemon(int slot) {
        return team[slot - 1];
    }

    public TrainerPokemon[] getTeam() {
        return team;
    }

    public void swap(int from, int to) {
        TrainerPokemon old = team[from];
        team[from] = team[to];
        team[to] = old;
    }

    public void setPokemon(int slot, TrainerPokemon pokemon) {
        team[slot - 1] = pokemon;
    }

    public int getSize() {
        int i = 0;
        while (i < team.length && team[i] != null) i++;
        return i;
    }

    @Nonnull
    @Override
    public DataArray toDataArray() {
        return data;
    }
}
