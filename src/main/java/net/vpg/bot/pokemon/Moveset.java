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
import net.vpg.bot.entities.Move;

import javax.annotation.Nonnull;

public class Moveset implements SerializableArray {
    private final DataArray data;
    private final PlayableMove[] moves = new PlayableMove[4];

    public Moveset(DataArray data) {
        this.data = data;
        for (int i = 0; i < data.length(); i++) {
            moves[i] = new PlayableMove(data.getObject(i));
        }
    }

    public Moveset() {
        this.data = DataArray.empty();
    }

    public Moveset setMove(int index, Move move) {
        PlayableMove playable = moves[index];
        if (playable == null) {
            playable = new PlayableMove(move, index);
            moves[index] = playable;
            data.add(playable);
        }
        playable.setMove(move);
        return this;
    }

    public PlayableMove[] getMoves() {
        return moves;
    }

    @Nonnull
    @Override
    public DataArray toDataArray() {
        return data;
    }
}
