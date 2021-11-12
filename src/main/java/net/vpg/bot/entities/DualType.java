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
package net.vpg.bot.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DualType implements Type {
    static Map<String, Map<String, DualType>> typeMap = new HashMap<>();

    static {
        for (SingleType primary : SingleType.values()) {
            typeMap.put(primary.getId(), new HashMap<>());
            for (SingleType secondary : SingleType.values()) {
                if (primary == secondary) continue;
                typeMap.get(primary.getId()).put(secondary.getId(), new DualType(primary, secondary));
            }
        }
    }

    SingleType primary;
    SingleType secondary;
    TypeMatchup matchup;
    List<Type> immune;
    List<Type> effective;

    DualType(SingleType primary, SingleType secondary) {
        this.primary = primary;
        this.secondary = secondary;
        matchup = new TypeMatchup();
        for (Type type : SingleType.values()) {
            matchup.put(type.getId(), primary.getMatchup().effectivenessAgainst(type.getId()) * secondary.getMatchup().effectivenessAgainst(type.getId()));
        }
        immune = matchup.filterEquals(0);
        effective = matchup.filterMoreThan(0);
    }

    public static DualType fromId(String id) {
        String[] types = id.split("-");
        assert types.length == 2;
        return typeMap.get(types[0]).get(types[1]);
    }

    @Override
    public String getName() {
        return primary.getName() + "/" + secondary.getName();
    }

    @Override
    public String getId() {
        return primary.getId() + "-" + secondary.getId();
    }

    @Override
    public List<Type> immuneTo() {
        return immune;
    }

    @Override
    public List<Type> effectiveAgainst() {
        return effective;
    }

    @Override
    public TypeMatchup getMatchup() {
        return matchup;
    }

    @Override
    public double multiplierReceivingDamage(Type type) {
        return primary.multiplierReceivingDamage(type) * secondary.multiplierReceivingDamage(type);
    }
}
