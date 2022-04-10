package net.vpg.bot.pokemon;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntToDoubleFunction;

public enum GrowthRate {
    SLOW("slow", x -> 5 * Math.pow(x, 3) / 4),
    MEDIUM("medium", x -> Math.pow(x, 3)),
    FAST("fast", x -> 4 * Math.pow(x, 3) / 5),
    MEDIUM_SLOW("medium-slow", x -> 6 * Math.pow(x, 3) / 5 - 15 * Math.pow(x, 2) - 100 * x - 140),
    ERRATIC("slow-then-very-fast", x -> {
        if (x < 50)
            return Math.pow(x, 3) * (100 - x) / 50;
        else if (x < 68)
            return Math.pow(x, 3) * (150 - x) / 100;
        else if (x < 98)
            return Math.pow(x, 3) * (1911 - 10 * x) / 1500;
        else
            return Math.pow(x, 3) * (160 - x) / 100;
    }),
    FLUCTUATING("fast-then-very-slow", x -> {
        if (x < 15)
            return Math.pow(x, 3) * (x + 73) / 150;
        else if (x < 36)
            return Math.pow(x, 3) * (x + 14) / 50;
        else
            return Math.pow(x, 3) * (x + 64) / 100;
    });

    private final String id;
    private final Map<Integer, Integer> expLookupTable;

    GrowthRate(String id, IntToDoubleFunction function) {
        this.id = id;
        this.expLookupTable = new HashMap<>();
        expLookupTable.put(1, 0);
        for (int i = 2; i <= 100; i++) {
            expLookupTable.put(i, (int) function.applyAsDouble(i));
        }
    }

    public static GrowthRate get(String id) {
        for (GrowthRate rate : values()) {
            if (rate.id.equals(id)) {
                return rate;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public int getTotalExpAtLevel(int level) {
        assert 1 <= level && level <= 100;
        return expLookupTable.get(level);
    }
}