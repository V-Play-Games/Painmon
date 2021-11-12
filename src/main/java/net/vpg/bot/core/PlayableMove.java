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

import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.entities.Move;

public class PlayableMove extends Move {
    int slot;

    public PlayableMove(DataObject data) {
        this(Move.get(data.getString("move")), data.getInt("slot"));
    }

    public PlayableMove(Move move, int slot) {
        super(move);
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public PlayableMove setSlot(int slot) {
        this.slot = slot;
        return this;
    }

    public PlayableMove setPP(int pp) {
        this.pp = pp;
        return this;
    }
}
