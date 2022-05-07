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
