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
import java.util.function.Consumer;

public enum Stat {
    HP("hp", true),
    ATTACK("attack", true),
    DEFENSE("defense", true),
    SP_ATK("special-attack", true),
    SP_DEF("special-defense", true),
    SPEED("speed", true),
    EVASION("evasion", false),
    ACCURACY("accuracy", false),
    CRIT_RATE("crit-rate", false);

    private static final Map<String, Stat> LOOKUP = MiscUtil.getEnumMap(Stat.class, Stat::getKey);
    final String key;
    final boolean isPermanent;

    Stat(String key, boolean isPermanent) {
        this.key = key;
        this.isPermanent = isPermanent;
    }

    public static Stat fromKey(String id) {
        return LOOKUP.get(id);
    }

    public String getKey() {
        return key;
    }

    public boolean isPermanent() {
        return isPermanent;
    }

    public static void forEach(boolean onlyPermanent, Consumer<Stat> action) {
        for (Stat stat : values()) {
            if (stat.isPermanent() || !onlyPermanent) {
                action.accept(stat);
            }
        }
    }
}
