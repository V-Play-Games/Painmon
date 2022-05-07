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
