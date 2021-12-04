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
import net.vpg.bot.framework.Util;

import java.util.List;
import java.util.Map;

public enum SingleType implements Type {
    NORMAL(new TypeMatchup()
        .put("rock", 0.5)
        .put("ghost", 0)
        .put("steel", 0.5)),
    FIRE(new TypeMatchup()
        .put("fire", 0.5)
        .put("water", 0.5)
        .put("grass", 2)
        .put("ice", 2)
        .put("bug", 2)
        .put("rock", 0.5)
        .put("dragon", 0.5)
        .put("steel", 2)),
    WATER(new TypeMatchup()
        .put("fire", 2)
        .put("water", 0.5)
        .put("grass", 0.5)
        .put("ground", 2)
        .put("rock", 2)
        .put("dragon", 0.5)),
    ELECTRIC(new TypeMatchup()
        .put("water", 2)
        .put("electric", 0.5)
        .put("grass", 0.5)
        .put("ground", 0)
        .put("flying", 2)
        .put("dragon", 0.5)),
    GRASS(new TypeMatchup()
        .put("ground", 2)
        .put("fire", 0.5)
        .put("water", 2)),
    ICE(new TypeMatchup()
        .put("dragon", 2)
        .put("ground", 2)),
    FIGHTING(new TypeMatchup()
        .put("normal", 2)
        .put("psychic", 0.5)
        .put("fairy", 0.5)
        .put("dark", 2)
        .put("steel", 2)
        .put("ghost", 0)
        .put("flying", 0.5)),
    POISON(new TypeMatchup()
        .put("steel", 0)),
    GROUND(new TypeMatchup()
        .put("electric", 2)
        .put("steel", 2)
        .put("poison", 2)
        .put("flying", 0)),
    FLYING(new TypeMatchup()
        .put("ground", 2)
        .put("fighting", 2)),
    PSYCHIC(new TypeMatchup()
        .put("dark", 0)
        .put("poison", 0)),
    BUG(new TypeMatchup()
        .put("psychic", 2)),
    ROCK(new TypeMatchup()
        .put("flying", 2)
        .put("grass", 0.5)),
    GHOST(new TypeMatchup()
        .put("normal", 0)
        .put("ghost", 2)
        .put("dark", 0.5)
        .put("psychic", 2)),
    DRAGON(new TypeMatchup()
        .put("dragon", 2)
        .put("fairy", 0)),
    DARK(new TypeMatchup()
        .put("psychic", 2)
        .put("ghost", 2)),
    STEEL(new TypeMatchup()
        .put("fairy", 2)),
    FAIRY(new TypeMatchup()
        .put("dragon", 2)
        .put("dark", 2)
        .put("fighting", 2));

    static final Map<String, SingleType> map = MiscUtil.getEnumMap(SingleType.class);
    TypeMatchup matchup;
    List<Type> immune;
    List<Type> effective;

    SingleType(TypeMatchup matchup) {
        this.matchup = matchup;
    }

    public static SingleType fromId(String id) {
        return map.get(id);
    }

    @Override
    public String getName() {
        return Util.toProperCase(toString());
    }

    @Override
    public String getId() {
        return toString().toLowerCase();
    }

    @Override
    public List<Type> immuneTo() {
        return immune == null ? immune = matchup.filterEquals(0) : immune;
    }

    @Override
    public List<Type> effectiveAgainst() {
        return effective == null ? effective = matchup.filterMoreThan(0) : effective;
    }

    @Override
    public TypeMatchup getMatchup() {
        return matchup;
    }

    @Override
    public double multiplierReceivingDamage(Type type) {
        return type.getMatchup().effectivenessAgainst(this.getId());
    }
}
