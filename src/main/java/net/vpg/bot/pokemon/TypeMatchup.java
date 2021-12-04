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
import java.util.stream.Collectors;

public class TypeMatchup {
    Map<String, Double> matchup = new HashMap<>();

    public TypeMatchup put(String type, double multiplier) {
        matchup.put(type, multiplier);
        return this;
    }

    public double effectivenessAgainst(String type) {
        return matchup.getOrDefault(type, 1.0);
    }

    public Map<String, Double> getMap() {
        return matchup;
    }

    public List<Type> filterEquals(double multiplier) {
        return matchup.entrySet()
            .stream()
            .filter(e -> e.getValue() == multiplier)
            .map(Map.Entry::getKey)
            .map(SingleType::fromId)
            .collect(Collectors.toList());
    }

    public List<Type> filterMoreThan(double multiplier) {
        return matchup.entrySet()
            .stream()
            .filter(e -> e.getValue() > multiplier)
            .map(Map.Entry::getKey)
            .map(SingleType::fromId)
            .collect(Collectors.toList());
    }

    public List<Type> filterLessThan(double multiplier) {
        return matchup.entrySet()
            .stream()
            .filter(e -> e.getValue() < multiplier)
            .map(Map.Entry::getKey)
            .map(SingleType::fromId)
            .collect(Collectors.toList());
    }
}
