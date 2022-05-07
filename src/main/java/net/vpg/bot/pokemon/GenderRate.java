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

import net.vpg.bot.core.Range;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static net.vpg.bot.pokemon.Gender.*;

public class GenderRate {
    private static final Map<Integer, GenderRate> RATES = IntStream.rangeClosed(-1, 8).boxed().collect(Collectors.toMap(i -> i, GenderRate::new));
    private static final Range GENDER_RANGE = Range.of(1, 8);
    private final int femaleRate;

    private GenderRate(int femaleRate) {
        this.femaleRate = femaleRate;
    }

    public static GenderRate of(int femaleRate) {
        assert -1 <= femaleRate && femaleRate <= 8;
        return RATES.get(femaleRate);
    }

    public int getFemaleRate() {
        return femaleRate;
    }

    public Gender generate() {
        switch (femaleRate) {
            case -1:
                return GENDERLESS;
            case 0:
                return MALE;
            case 8:
                return FEMALE;
            default:
                return GENDER_RANGE.random() > femaleRate ? MALE : FEMALE;
        }
    }
}
