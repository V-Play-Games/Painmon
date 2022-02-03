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

import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.entities.Entity;
import net.vpg.bot.entities.Move;

import javax.annotation.Nonnull;

public class PlayableMove implements Entity {
    private final DataObject data;
    private final int slot;
    private Move move;
    private int pp;

    public PlayableMove(DataObject data) {
        this.data = data;
        this.move = Move.get(data.getString("move"));
        this.slot = data.getInt("slot");
        this.pp = data.getInt("pp");
    }

    public PlayableMove(Move move, int slot) {
        this(move, slot, move.getPP());
    }

    public PlayableMove(Move move, int slot, int pp) {
        this.move = move;
        this.slot = slot;
        this.pp = pp;
        this.data = DataObject.empty()
            .put("", move.getId())
            .put("slot", slot)
            .put("pp", pp);
    }

    public PlayableMove setMove(Move move) {
        return setMove(move, move.getPP());
    }

    public PlayableMove setMove(Move move, int pp) {
        this.move = move;
        data.put("move", move.getId());
        setPP(pp);
        return this;
    }

    @Override
    public String getId() {
        return move.getId();
    }

    public int getSlot() {
        return slot;
    }

    public int getPP() {
        return pp;
    }

    public PlayableMove setPP(int pp) {
        this.pp = pp;
        data.put("pp", pp);
        return this;
    }

    @Nonnull
    @Override
    public DataObject toData() {
        return data;
    }
}
