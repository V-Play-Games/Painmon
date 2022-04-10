package net.vpg.bot.pokemon;

public enum StatusCondition {
    NONE(false),
    PARALYSIS(true),
    FREEZE(true),
    BURN(true),
    POISON(true),
    TOXIC_POISON(true),
    SLEEP(true);

    private final boolean isVolatile;

    StatusCondition(boolean isVolatile) {
        this.isVolatile = isVolatile;
    }

    public boolean isVolatile() {
        return isVolatile;
    }
}
