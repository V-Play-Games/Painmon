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
import net.vpg.bot.core.MiscUtil;
import net.vpg.bot.entities.Entity;
import net.vpg.bot.entities.EntityReference;
import net.vpg.bot.entities.Move;

import javax.annotation.Nonnull;

public class PlayableMove implements Entity {
    private final DataObject data;
    private final int index;
    private Move move;
    private EntityReference<Move> reference; // Store reference instead of move directly to avoid initialization issues
    private int currentPP;
    private int maxPP;

    public PlayableMove(DataObject data) {
        this.data = data;
        this.reference = new EntityReference<>(Move.INFO, data.getString("move"));
        this.move = reference.get();
        this.index = data.getInt("index");
        if (move != null) {
            this.maxPP = data.getInt("maxPP", move.getPP());
            this.currentPP = data.getInt("currentPP", maxPP);
        }
    }

    public PlayableMove(Move move, int index) {
        this(move, index, move.getPP());
    }

    public PlayableMove(Move move, int index, int maxPP) {
        this.data = DataObject.empty().put("slot", index);
        this.index = index;
        setMove(move, maxPP);
    }

    public Move getMove() {
        return move == null ? reference.get() : move;
    }

    public PlayableMove setMove(Move move) {
        return setMove(move, move.getPP());
    }

    public PlayableMove setMove(Move move, int pp) {
        this.move = move;
        data.put("move", move.getId());
        setCurrentPP(pp);
        setMaxPP(pp);
        return this;
    }

    public void swapWith(PlayableMove move) {
        MiscUtil.swapFields(this, move, PlayableMove::getMove, PlayableMove::setMove);
        MiscUtil.swapFields(this, move, PlayableMove::getCurrentPP, PlayableMove::setCurrentPP);
        MiscUtil.swapFields(this, move, PlayableMove::getMaxPP, PlayableMove::setMaxPP);
    }

    public int getCurrentPP() {
        return currentPP;
    }

    public void setCurrentPP(int currentPP) {
        this.currentPP = currentPP;
        data.put("currentPP", currentPP);
    }

    public int getMaxPP() {
        return maxPP;
    }

    public void setMaxPP(int maxPP) {
        this.maxPP = maxPP;
        data.put("maxPP", maxPP);
    }

    @Override
    public String getId() {
        return getMove().getId();
    }

    public int getIndex() {
        return index;
    }

    @Nonnull
    @Override
    public DataObject toData() {
        return data;
    }
}
