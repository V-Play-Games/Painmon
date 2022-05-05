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

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class MiscUtil {
    private MiscUtil() {
        // the private constructor to prevent instantiation
    }

    public static <K, V extends Enum<V>> Map<K, V> getEnumMap(Class<V> clazz, Function<V, K> keyFunction) {
        return EnumSet.allOf(clazz)
            .stream()
            .collect(Collectors.toMap(keyFunction, UnaryOperator.identity()));
    }

    public static <V extends Enum<V>> Map<String, V> getEnumMap(Class<V> clazz) {
        return getEnumMap(clazz, obj -> obj.name().toLowerCase());
    }

    public static <T, V> void swapFields(T t1, T t2, Function<T, V> getter, BiConsumer<T, V> setter) {
        V temp = getter.apply(t1);
        setter.accept(t1, getter.apply(t2));
        setter.accept(t2, temp);
    }

    public static class MapBuilder<K, V> {
        private final Map<K, V> map = new HashMap<>();

        public MapBuilder<K, V> put(K key, V value) {
            map.put(key, value);
            return this;
        }

        public Map<K, V> toMap() {
            return map;
        }
    }
}
