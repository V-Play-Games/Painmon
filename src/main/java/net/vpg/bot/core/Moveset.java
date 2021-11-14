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
package net.vpg.bot.core;

import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.SerializableArray;
import net.vpg.bot.entities.Move;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Moveset implements SerializableArray {
    private final DataArray data;
    private List<PlayableMove> cache;
    private PlayableMove move1;
    private PlayableMove move2;
    private PlayableMove move3;
    private PlayableMove move4;

    public Moveset(DataArray data) {
        this.data = data;
        prepareCache();
        data.toList().clear();
        switch (cache.size()) {
            case 4:
                data.add(move4 = cache.get(3));
            case 3:
                data.add(move3 = cache.get(2));
            case 2:
                data.add(move2 = cache.get(1));
            case 1:
                data.add(move1 = cache.get(0));
        }
        generateTackles();
        prepareCache();
    }

    public Moveset() {
        this.data = DataArray.empty();
        generateTackles();
        prepareCache();
    }

    private void prepareCache() {
        this.cache = data.stream(DataArray::getObject)
            .map(PlayableMove::new)
            .sorted(Comparator.comparingInt(PlayableMove::getSlot))
            .collect(Collectors.toList());
    }

    private void generateTackles() {
        if (move1 == null) data.add(move1 = new PlayableMove(Move.get("tackle"), 1));
        if (move2 == null) data.add(move2 = new PlayableMove(Move.get("tackle"), 2));
        if (move3 == null) data.add(move3 = new PlayableMove(Move.get("tackle"), 3));
        if (move4 == null) data.add(move4 = new PlayableMove(Move.get("tackle"), 4));
    }

    public Moveset setMove(int slot, Move move) {
        assert 1 <= slot && slot <= 4;
        getMove(slot).adjust(move);
        return this;
    }

    public PlayableMove getMove(int slot) {
        return cache.get(slot - 1);
    }

    @Nonnull
    @Override
    public DataArray toDataArray() {
        return data;
    }
}
