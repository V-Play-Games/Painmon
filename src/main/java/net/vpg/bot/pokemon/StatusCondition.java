package net.vpg.bot.pokemon;

import net.vpg.bot.core.MiscUtil;

import java.util.Map;

public enum StatusCondition {
    NONE(true),
    PARALYSIS(true),
    FREEZE(true),
    BURN(true),
    POISON(true),
    TOXIC_POISON(true),
    SLEEP(true);

    private static final Map<Integer, StatusCondition> LOOKUP = MiscUtil.getEnumMap(StatusCondition.class, StatusCondition::ordinal);
    private final boolean isVolatile;

    StatusCondition(boolean isVolatile) {
        this.isVolatile = isVolatile;
    }

    public static StatusCondition fromKey(int key) {
        return LOOKUP.get(key);
    }

    public boolean isVolatile() {
        return isVolatile;
    }

    public boolean isPoison() {
        return this == TOXIC_POISON || this == POISON;
    }
}
