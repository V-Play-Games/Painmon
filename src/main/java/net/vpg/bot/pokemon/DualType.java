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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DualType implements Type {
    private static final Map<String, Map<String, DualType>> typeMap = new HashMap<>();
    private final SingleType primary;
    private final SingleType secondary;
    private final Matchup matchup;
    private final List<Type> immune;
    private final List<Type> effective;

    static {
        for (SingleType primary : SingleType.values()) {
            typeMap.put(primary.getId(), new HashMap<>());
            for (SingleType secondary : SingleType.values()) {
                if (primary == secondary) continue;
                typeMap.get(primary.getId()).put(secondary.getId(), new DualType(primary, secondary));
            }
        }
    }

    DualType(SingleType primary, SingleType secondary) {
        this.primary = primary;
        this.secondary = secondary;
        Matchup.Builder builder = new Matchup.Builder();
        Matchup primaryMatchup = primary.getMatchup();
        Matchup secondaryMatchup = secondary.getMatchup();
        for (Type type : SingleType.values()) {
            String id = type.getId();
            builder.put(id, primaryMatchup.multiplierAgainst(id) * secondaryMatchup.multiplierAgainst(id));
        }
        matchup = builder.build();
        immune = matchup.filter(Multiplier.IMMUNE);
        effective = matchup.filter(m -> m.getValue() > 0);
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
    public List<Type> immuneAgainst() {
        return immune;
    }

    @Override
    public List<Type> effectiveAgainst() {
        return effective;
    }

    @Override
    public Matchup getMatchup() {
        return matchup;
    }

    @Override
    public double multiplierReceivingDamage(Type type) {
        return type.multiplierAgainst(primary) * type.multiplierAgainst(secondary);
    }
}
