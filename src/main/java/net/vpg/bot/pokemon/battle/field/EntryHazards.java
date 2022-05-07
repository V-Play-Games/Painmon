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
package net.vpg.bot.pokemon.battle.field;

public enum EntryHazards implements Field.Effect {
    STEALTH_ROCK,
    TOXIC_SPIKES_1,
    TOXIC_SPIKES_2,
    SPIKES_1,
    SPIKES_2,
    SPIKES_3,
    STICKY_WEB;

    @Override
    public boolean isEntireField() {
        return false;
    }

    @Override
    public Type getType() {
        return Type.ENTRY_HAZARD;
    }
}
