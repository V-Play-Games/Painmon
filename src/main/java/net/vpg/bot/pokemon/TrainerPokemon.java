package net.vpg.bot.pokemon;

import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.entities.Pokemon;

import java.util.HashMap;
import java.util.Map;

public class TrainerPokemon extends PokemonData {
    public static final Map<String, PlayerPokemon> CACHE = new HashMap<>();
    protected final String trainerId;
    protected String nickname;

    public TrainerPokemon(DataObject data) {
        this(Pokemon.get(data.getString("base")), data.getString("id"), data);
    }

    public TrainerPokemon(Pokemon base, String id) {
        this(base, id, DataObject.empty()
            .put("id", id)
            .put("base", base.getId()));
    }

    public TrainerPokemon(Pokemon base, String id, DataObject data) {
        super(base, id, data);
        this.trainerId = data.getString("trainerId");
        setNickname(data.getString("nickname", null));
    }

    @Override
    public Type getType() {
        return Type.TRAINER;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        data.put("nickname", nickname);
    }

    public String getEffectiveName() {
        return nickname != null ? nickname : base.getName();
    }
}
