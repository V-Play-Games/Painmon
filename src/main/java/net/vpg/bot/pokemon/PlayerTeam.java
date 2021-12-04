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
import net.vpg.bot.entities.PlayablePokemon;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlayerTeam implements SerializableArray {
    final DataArray data;
    final List<PlayablePokemon> team;

    public PlayerTeam(DataArray data) {
        this.data = data;
        this.team = data.stream(DataArray::getString).map(PlayablePokemon::get).collect(Collectors.toList());
    }

    public PlayerTeam() {
        this.data = DataArray.empty();
        this.team = new ArrayList<>(6);
        clearTeam();
    }

    public PlayablePokemon getLead() {
        return team.get(0);
    }

    public PlayablePokemon getPokemon(int slot) {
        return team.get(slot - 1);
    }

    public List<PlayablePokemon> getTeam() {
        return team;
    }

    public void fillGaps() {
        List<PlayablePokemon> mons = team.stream().filter(Objects::nonNull).collect(Collectors.toList());
        clearTeam();
        for (int i = 0; i < mons.size(); i++) {
            team.set(i, mons.get(i).setSlot(i + 1));
        }
    }

    public void swap(int from, int to) {
        Collections.swap(team, from, to);
        fillGaps();
    }

    public void setPokemon(int slot, PlayablePokemon pokemon) {
        assert 1 <= slot && slot <= 6;
        team.set(slot - 1, pokemon);
    }

    public void clearTeam() {
        team.clear();
        Collections.addAll(team, null, null, null, null, null, null);
    }

    public int getSize() {
        return (int) team.stream().filter(Objects::nonNull).count();
    }

    @Nonnull
    @Override
    public DataArray toDataArray() {
        return data;
    }
}
