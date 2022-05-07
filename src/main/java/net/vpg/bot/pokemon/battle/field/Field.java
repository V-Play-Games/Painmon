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
package net.vpg.bot.pokemon.battle.field;

import java.util.*;

public class Field {
    private final Set<Effect> effects = new HashSet<>();
    private final Type type;

    public Field(Type type) {
        this.type = type;
    }

    public static Map<Type, Field> makeFieldMap(Type... types) {
        Map<Type, Field> map = new HashMap<>();
        for (Type type : types) {
            map.put(type, new Field(type));
        }
        return map;
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
        ENTIRE_FIELD, PLAYER, NPC;

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
