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
package net.vpg.bot.core;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.EnumSet;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class MiscUtil {
    private MiscUtil() {
        // the private constructor to prevent instantiation
    }

    public static <T extends Enum<T>> Map<String, T> getEnumMap(Class<T> clazz) {
        return EnumSet.allOf(clazz)
            .stream()
            .collect(Collectors.toMap(Enum::name, UnaryOperator.identity(),
                (n1, n2) -> {
                    throw new Error();
                }, CaseInsensitiveMap::new));
    }
}