package net.vpg.bot.pokemon.battle.field;

public enum Terrain implements EntireField.Effect {
    NONE,
    ELECTRIC,
    GRASSY,
    PSYCHIC,
    MISTY,
    // --- New ---
    LUNAR,
    DUSTY,
    MAGNETIC,
    ICY,
    ACIDIC,
    BREEZY,
    SPOOKY,
    DARK,
    BUG;

    @Override
    public Type getType() {
        return Type.TERRAIN;
    }
}
