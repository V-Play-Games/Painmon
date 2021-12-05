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

import static net.vpg.bot.pokemon.Stat.*;

@SuppressWarnings("unused")
public enum Nature {
    //@formatter:off
    //NAME | +++++ | ------ |
    HARDY  (ATTACK,  ATTACK ),
    LONELY (ATTACK,  DEFENSE),
    BRAVE  (ATTACK,  SP_ATK ),
    ADAMANT(ATTACK,  SP_DEF ),
    NAUGHTY(ATTACK,  SPEED  ),
    BOLD   (DEFENSE, ATTACK ),
    DOCILE (DEFENSE, DEFENSE),
    RELAXED(DEFENSE, SP_ATK ),
    IMPISH (DEFENSE, SP_DEF ),
    LAX    (DEFENSE, SPEED  ),
    TIMID  (SP_ATK,  ATTACK ),
    HASTY  (SP_ATK,  DEFENSE),
    SERIOUS(SP_ATK,  SP_ATK ),
    JOLLY  (SP_ATK,  SP_DEF ),
    NAIVE  (SP_ATK,  SPEED  ),
    MODEST (SP_DEF,  ATTACK ),
    MILD   (SP_DEF,  DEFENSE),
    QUIET  (SP_DEF,  SP_ATK ),
    BASHFUL(SP_DEF,  SP_DEF ),
    RASH   (SP_DEF,  SPEED  ),
    CALM   (SPEED,   ATTACK ),
    GENTLE (SPEED,   DEFENSE),
    SASSY  (SPEED,   SP_ATK ),
    CAREFUL(SPEED,   SP_DEF ),
    QUIRKY (SPEED,   SPEED  ),
    UNKNOWN(null, null);
    //@formatter:on

    static final Map<String, Nature> map = MiscUtil.getEnumMap(Nature.class);
    final Stat increasedStat;
    final Stat decreasedStat;

    Nature(Stat increasedStat, Stat decreasedStat) {
        this.increasedStat = increasedStat;
        this.decreasedStat = decreasedStat;
    }

    public static Nature fromKey(String key) {
        return map.getOrDefault(key, UNKNOWN);
    }
}
