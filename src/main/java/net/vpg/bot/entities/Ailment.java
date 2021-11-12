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
package net.vpg.bot.entities;

public enum Ailment {
    UNKNOWN("Unknown"),
    NONE("None"),
    PARALYSIS("paralyzed"),
    SLEEP("asleep"),
    FREEZE("frozen"),
    BURN("burnt"),
    POISON("poisoned"),
    BADLY_POISON("badly poisoned"),
    CONFUSION("confused"),
    INFATUATION("infatuated"),
    TRAP("trapped"),
    NIGHTMARE("nightmare"),
    TORMENT("tormented"),
    DISABLE("disabled"),
    YAWN("yawning"),
    SILENCE("silenced"),
    HEAL_BLOCK("heal blocked"),
    NO_TYPE_IMMUNITY("no type immunity"),
    LEECH_SEED("leech seeded"),
    EMBARGO("embargo"),
    PERISH_SONG("Perish Song"),
    INGRAIN("Ingrain"),
    TELEKINESIS("Telekinesis"),
    NO_GROUND_IMMUNITY("No Ground Immunity"),
    ;

    String id;
    String name;

    Ailment(String name) {
        this.name = name;
        this.id = toString().toLowerCase().replaceAll("_", "-");
    }

    public static Ailment fromId(String key) {
        for (Ailment ailment : values()) {
            if (ailment.getId().equals(key)) {
                return ailment;
            }
        }
        throw new IllegalArgumentException(key + " ailment?");
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
