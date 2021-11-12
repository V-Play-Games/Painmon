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

import net.vpg.bot.framework.Util;

import java.util.List;

public class Condition {
    public static boolean doesFulfill(List<String> conditions, Player player) {
        return conditions.stream()
            .map(s -> {
                String method = Util.getMethod(s);
                String[] args = Util.getArgs(s).split(";");
                switch (method) {
                    case "equals":
                        return player.resolveReferences(args[0]).equals(player.resolveReferences(args[1]));
                    case "return":
                        return Boolean.parseBoolean(args[0]);
                    default:
                        return false;
                }
            })
            .reduce(Boolean::logicalAnd)
            .orElse(false);
    }
}
