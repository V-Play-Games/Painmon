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

/**
 * A range of integers from the lower bound (inclusive) to the upper bound (inclusive)
 */
public class Range {
    private final int upper;
    private final int lower;

    private Range(int lower, int upper) {
        assert lower <= upper;
        this.lower = lower;
        this.upper = upper;
    }

    /**
     * Creates a new range for the given bounds
     *
     * @param lower the lower bound (inclusive)
     * @param upper the upper bound (inclusive)
     */
    public static Range of(int lower, int upper) {
        return new Range(lower, upper);
    }

    /**
     * Creates a new range for the bounds given in the string
     *
     * @param range the upper and lower bound (both inclusive), separated with a hyphen
     */
    public static Range of(String range) {
        int hyphen = range.indexOf('-');
        return new Range(Integer.parseInt(range.substring(0, hyphen)), Integer.parseInt(range.substring(hyphen + 1)));
    }

    /**
     * Returns the lower bound of this range
     *
     * @return the lower bound of this range
     */
    public int getLower() {
        return lower;
    }

    /**
     * Returns the upper bound of this range
     *
     * @return the upper bound of this range
     */
    public int getUpper() {
        return upper;
    }

    /**
     * Generates a random number between the upper and lower bounds (both inclusive) of this range
     *
     * @return a random number between the upper and lower bounds (both inclusive) of this range
     */
    public int random() {
        return (int) Math.round(Math.random() * (upper - lower)) + lower;
    }

    /**
     * Checks if the given number is less than or equal to the upper bound and
     * more than or equal to the lower bound of this range.
     * It is equivalent to {@code getLower() <= i && i <= getUpper()}
     *
     * @param i the number to check
     * @return true, if the given number is between the upper and lower bounds (both inclusive)
     * of this range, false otherwise
     */
    public boolean contains(int i) {
        return lower <= i && i <= upper;
    }

    @Override
    public String toString() {
        return lower + "-" + upper;
    }
}
