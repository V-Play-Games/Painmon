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

public class Range {
    private final int upper;
    private final int lower;

    private Range(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public static Range of(int lower, int upper) {
        return new Range(lower, upper);
    }

    public static Range of(String range) {
        int hyphen = range.indexOf('-');
        return new Range(Integer.parseInt(range.substring(0, hyphen)), Integer.parseInt(range.substring(hyphen + 1)));
    }

    public int getLower() {
        return lower;
    }

    public int getUpper() {
        return upper;
    }

    public int random() {
        return (int) Math.round(Math.random() * (upper - lower) + lower);
    }

    public boolean contains(int i) {
        return lower <= i && i <= upper;
    }

    @Override
    public String toString() {
        return lower + "-" + upper;
    }
}
