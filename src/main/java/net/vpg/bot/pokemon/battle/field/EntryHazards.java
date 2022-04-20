package net.vpg.bot.pokemon.battle.field;

public enum EntryHazards implements Field.Effect {
    STEALTH_ROCK,
    TOXIC_SPIKES_1,
    TOXIC_SPIKES_2,
    SPIKES_1,
    SPIKES_2,
    SPIKES_3,
    STICKY_WEB;

    @Override
    public boolean isEntireField() {
        return false;
    }

    @Override
    public Type getType() {
        return Type.ENTRY_HAZARD;
    }
}
