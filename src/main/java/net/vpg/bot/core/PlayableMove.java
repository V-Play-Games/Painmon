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
