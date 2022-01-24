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

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface Type {
    static Type fromId(String id) {
        return id.contains("-") ? DualType.fromId(id) : SingleType.fromId(id);
    }

    String getName();

    String getId();

    List<Type> immuneAgainst();

    List<Type> effectiveAgainst();

    Matchup getMatchup();

    default double multiplierAgainst(Type type) {
        return multiplierAgainst(type.getId());
    }

    default double multiplierAgainst(String type) {
        return getMatchup().multiplierAgainst(type);
    }

    double multiplierReceivingDamage(Type type);

    enum Multiplier {
        DOUBLE_EFFECTIVE(4),
        EFFECTIVE(2),
        NEUTRAL(1),
        IMMUNE(0),
        RESISTANT(0.5),
        DOUBLE_RESISTANT(0.25);
        private static final Multiplier[] multipliers = values();
        private final double value;

        Multiplier(double value) {
            this.value = value;
        }

        public static Multiplier of(double value) {
            for (Multiplier multiplier : multipliers) {
                if (multiplier.value == value) {
                    return multiplier;
                }
            }
            throw new IllegalArgumentException("No multiplier for " + value);
        }

        public double getValue() {
            return value;
        }
    }

    class Matchup {
        private final Map<String, Multiplier> matchup;

        public Matchup(Map<String, Multiplier> matchup) {
            this.matchup = Collections.unmodifiableMap(matchup);
        }

        public double multiplierAgainst(String type) {
            return matchup.getOrDefault(type, Multiplier.NEUTRAL).value;
        }

        public Map<String, Multiplier> getMap() {
            return matchup;
        }

        public List<Type> filter(Predicate<Multiplier> multiplier) {
            return matchup.entrySet()
                .stream()
                .filter(e -> multiplier.test(e.getValue()))
                .map(Map.Entry::getKey)
                .map(SingleType::fromId)
                .collect(Collectors.toList());
        }

        public List<Type> filter(Multiplier filter) {
            return filter(multiplier -> multiplier == filter);
        }

        public static class Builder {
            private final Map<String, Multiplier> matchup = new CaseInsensitiveMap<>();

            public Builder put(String type, double multiplier) {
                return put(type, Multiplier.of(multiplier));
            }

            public Builder put(String type, Multiplier multiplier) {
                matchup.put(type, multiplier);
                return this;
            }

            public Builder put(Type type, Multiplier multiplier) {
                return put(type.getId(), multiplier);
            }

            public Matchup build() {
                return new Matchup(matchup);
            }
        }
    }
}
