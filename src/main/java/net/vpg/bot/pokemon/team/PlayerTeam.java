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
import net.dv8tion.jda.api.utils.data.SerializableArray;
import net.vpg.bot.pokemon.PlayerPokemon;

import javax.annotation.Nonnull;
import java.util.List;

public class PlayerTeam extends PokemonTeam<PlayerPokemon> implements SerializableArray {
    protected final DataArray data;

    public PlayerTeam(DataArray data) {
        super(new PlayerPokemon[6]);
        this.data = data;
        for (int i = 0; i < data.length(); i++) {
            array[i] = PlayerPokemon.get(data.getString(i));
        }
    }

    public PlayerTeam() {
        this(DataArray.empty());
    }

    @Override
    public void setPokemon(int index, PlayerPokemon pokemon) {
        super.setPokemon(index, pokemon);
        while (data.length() <= index) data.add(null);
        data.toList().set(index, pokemon.getId());
    }

    @Override
    public boolean addPokemon(PlayerPokemon pokemon) {
        if (!super.addPokemon(pokemon)) {
            return false;
        }
        data.add(pokemon.getId());
        return true;
    }

    public void swap(int from, int to) {
        PlayerPokemon old = array[from];
        array[from] = array[to];
        array[to] = old;
        List<Object> list = data.toList();
        list.set(from, array[from].getId());
        list.set(to, array[to].getId());
    }

    @Nonnull
    @Override
    public DataArray toDataArray() {
        return data;
    }
}
