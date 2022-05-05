package net.vpg.bot.entities;

import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.core.Bot;
import net.vpg.bot.pokemon.PlayerPokemon;
import net.vpg.bot.pokemon.PokemonData;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GiftPokemon extends PokemonData {
    public static final Map<String, GiftPokemon> CACHE = new HashMap<>();
    public static final EntityInfo<GiftPokemon> INFO = new EntityInfo<>(
        GiftPokemon.class.getResource("gift-pokemon.json"),
        (Function<DataObject, GiftPokemon>) GiftPokemon::new,
        CACHE
    );
    protected final boolean useDefaultShiny;
    protected final boolean useDefaultIvs;
    protected final boolean useDefaultGender;

    public GiftPokemon(DataObject data) {
        this(data, !data.isNull("ivs"));
    }

    private GiftPokemon(DataObject data, boolean useDefaultIvs) {
        super(data);
        this.useDefaultShiny = data.isNull("shiny");
        this.useDefaultIvs = useDefaultIvs;
        this.useDefaultGender = data.isNull("gender");
    }

    public static GiftPokemon get(String id) {
        return CACHE.get(id);
    }

    @Override
    public PlayerPokemon giveTo(Player player, String id, Bot bot) {
        PlayerPokemon pokemon = super.giveTo(player, id, bot);
        pokemon.randomize();
        if (useDefaultShiny)
            pokemon.setShiny(shiny);
        if (nature != null)
            pokemon.setNature(nature);
        if (ability != null)
            pokemon.setAbility(ability);
        if (useDefaultGender)
            pokemon.setGender(gender);
        if (useDefaultIvs)
            pokemon.getIvs().copyFrom(ivs);
        return pokemon;
    }

    @Override
    public Type getDataType() {
        return null;
    }
}
