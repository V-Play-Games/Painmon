package net.vpg.bot.pokemon.battle.field;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Field {
    private final Set<Effect> effects = new HashSet<>();
    private final Type type;

    public Field(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public void addEffect(Effect effect) {
        checkValid(effect);
        effects.add(effect);
    }

    public void removeEffect(Effect effect) {
        checkValid(effect);
        effects.remove(effect);
    }

    public Set<Effect> getEffects() {
        return Collections.unmodifiableSet(effects);
    }

    protected void checkValid(Effect effect) {
        if (effect.isEntireField() != type.isEntireField()) {
            throw new IllegalArgumentException("Cannot add " + effect + " with entireField = " + !type.isEntireField());
        }
    }

    public enum Type {
        ENTIRE_FIELD, PLAYER, NPC, PLAYER_1, PLAYER_2;

        public boolean isEntireField() {
            return this == ENTIRE_FIELD;
        }
    }

    public interface Effect {
        boolean isEntireField();

        Type getType();

        enum Type {
            WEATHER, TERRAIN, ZONE, ENVIRONMENT, ENTRY_HAZARD, MISC
        }
    }
}
