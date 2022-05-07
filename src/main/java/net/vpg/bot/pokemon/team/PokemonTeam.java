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
