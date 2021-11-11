package net.vpg.bot.core;

import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.SerializableArray;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerTeam implements SerializableArray {
    final DataArray data;
    final List<PlayablePokemon> cache;

    public PlayerTeam(DataArray data) {
        this.data = data;
        this.cache = data.stream(DataArray::getString).map(PlayablePokemon::get).collect(Collectors.toList());
    }

    public PlayerTeam() {
        this.data = DataArray.empty();
        this.cache = new ArrayList<>();
    }

    @Nonnull
    @Override
    public DataArray toDataArray() {
        return data;
    }
}
