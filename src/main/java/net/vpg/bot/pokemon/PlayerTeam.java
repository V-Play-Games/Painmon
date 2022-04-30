package net.vpg.bot.pokemon;

import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.SerializableArray;

import javax.annotation.Nonnull;
import java.util.List;

public class PlayerTeam extends PokemonTeam implements SerializableArray {
    protected final DataArray data;

    public PlayerTeam(DataArray data) {
        this.data = data;
        for (int i = 0, size = data.toList().size(); i < size; i++) {
            pokemons[i] = PlayerPokemon.get(data.getString(i));
        }
    }

    public PlayerTeam() {
        this.data = DataArray.empty();
    }

    @Override
    public void setPokemon(int index, TrainerPokemon pokemon) {
        super.setPokemon(index, pokemon);
        data.toList().set(index, pokemon.getId());
    }

    public void swap(int from, int to) {
        TrainerPokemon old = pokemons[from];
        pokemons[from] = pokemons[to];
        pokemons[to] = old;
        List<Object> list = data.toList();
        list.set(from, pokemons[from].getId());
        list.set(to, pokemons[to].getId());
    }

    @Nonnull
    @Override
    public DataArray toDataArray() {
        return data;
    }
}
