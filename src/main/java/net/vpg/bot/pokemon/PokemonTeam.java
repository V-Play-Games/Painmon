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

public class PokemonTeam {
    protected final TrainerPokemon[] pokemons = new TrainerPokemon[6];

    public TrainerPokemon[] getPokemon() {
        return pokemons;
    }

    public TrainerPokemon getPokemon(int index) {
        return pokemons[index];
    }

    public void setPokemon(int index, TrainerPokemon pokemon) {
        pokemons[index] = pokemon;
    }

    public TrainerPokemon getLead() {
        return pokemons[0];
    }

    public int getSize() {
        int i = 0;
        while (i < pokemons.length && pokemons[i] != null) i++;
        return i;
    }
}
