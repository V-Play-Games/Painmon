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

import net.vpg.bot.pokemon.TrainerPokemon;

public class PokemonTeam<T extends TrainerPokemon> {
    protected final T[] array;

    public PokemonTeam(T[] array) {
        this.array = array;
    }

    public T[] getPokemon() {
        return array;
    }

    public T getPokemon(int index) {
        return array[index];
    }

    public void setPokemon(int index, T pokemon) {
        array[index] = pokemon;
    }

    public boolean addPokemon(T pokemon) {
        int size = getSize();
        if (size == array.length) {
            return false;
        }
        array[size] = pokemon;
        return true;
    }

    public T getLead() {
        return array[0];
    }

    public int getSize() {
        int i = 0;
        while (i < array.length && array[i] != null) i++;
        return i;
    }
}
