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

import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.core.Util;
import net.vpg.bot.pokemon.Type;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class Move implements Entity {
    public static final Map<String, Move> CACHE = new HashMap<>();
    public static final EntityInfo<Move> INFO = new EntityInfo<>(Move.class.getResource("move.json"), Move::new, CACHE);
    private final DataObject data;
    private final int pp;
    private final int accuracy;
    private final String description;
    private final int priority;
    private final Type type;
    private final Target target;
    private final String effect;
    private final String name;
    private final String id;
    private final int effectChance;
    private final int power;
    private final Category category;
    private final Metadata metadata;

    public Move(Move copy) {
        this.data = copy.data;
        this.effectChance = copy.effectChance;
        this.description = copy.description;
        this.effect = copy.effect;
        this.id = copy.id;
        this.name = copy.name;
        this.pp = copy.pp;
        this.accuracy = copy.accuracy;
        this.priority = copy.priority;
        this.power = copy.power;
        this.type = copy.type;
        this.target = copy.target;
        this.category = copy.category;
        this.metadata = copy.metadata;
    }

    public Move(DataObject data) {
        this.data = data;
        this.effectChance = data.getInt("effectChance", 0);
        this.description = data.getString("description").replace("$effect_chance", String.valueOf(effectChance));
        this.effect = data.getString("effect");
        this.id = data.getString("name");
        this.name = Util.toProperCase(id.replaceAll("-", " "));
        this.pp = data.getInt("pp", 0);
        this.accuracy = data.getInt("accuracy", 0);
        this.priority = data.getInt("priority", 0);
        this.power = data.getInt("power", 0);
        this.type = Type.fromId(data.getString("type"));
        this.target = Target.fromKey(data.getString("target"));
        this.category = Category.fromKey(data.getString("category"));
        this.metadata = new Metadata(data.getObject("meta"));
    }

    public static Move get(String id) {
        return CACHE.get(id);
    }

    public int getPP() {
        return pp;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public Type getType() {
        return type;
    }

    public Target getTarget() {
        return target;
    }

    public String getEffect() {
        return effect;
    }

    public String getName() {
        return name;
    }

    public int getEffectChance() {
        return effectChance;
    }

    public int getPower() {
        return power;
    }

    public Category getCategory() {
        return category;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    @Override
    public String getId() {
        return id;
    }

    @Nonnull
    @Override
    public DataObject toData() {
        return data;
    }

    public enum Target {
        ALLY("Ally"),
        USER("User"),
        USER_OR_ALLY("User or Ally"),
        USER_AND_ALLIES("User and Allies"),
        SELECTED_POKEMON("Selected Pokemon"),
        SELECTED_POKEMON_ME_FIRST("Selected Pokemon (Me First)"),
        RANDOM_OPPONENT("Random Opponent"),
        ENTIRE_FIELD("Entire Field"),
        USERS_FIELD("User's Field"),
        OPPONENTS_FIELD("Opponents' Field"),
        ALL_OPPONENTS("All Opponents"),
        ALL_OTHER_POKEMON("All Other Pokemon"),
        ALL_POKEMON("All Pokemon"),
        ALL_ALLIES("All Allies"),
        SPECIFIC_MOVE("Specific Move"),
        ;

        String key;
        String name;

        Target(String name) {
            this.name = name;
            this.key = toString().toLowerCase().replaceAll("_", "-");
        }

        public static Target fromKey(String key) {
            for (Target target : values()) {
                if (target.getKey().equals(key)) {
                    return target;
                }
            }
            throw new IllegalArgumentException(key + " target?");
        }

        public String getName() {
            return name;
        }

        public String getKey() {
            return key;
        }
    }

    public enum EffectCategory {
        FORCE_SWITCH("Force Switch", "force-switch"),
        DAMAGE("Damage", "damage"),
        DAMAGE_AND_AILMENT("Damage + Ailment", "damage+ailment"),
        DAMAGE_AND_LOWER_STATS("Damage + Lower Stats", "damage+lower"),
        DAMAGE_AND_RAISE_STATS("Damage + Raise Stats", "damage+raise"),
        DAMAGE_AND_HEAL("Damage + Heal", "damage+heal"),
        OHKO("One-hit KO (OHKO)", "ohko"),
        AILMENT("Ailment", "ailment"),
        HEAL("Heal", "heal"),
        NET_GOOD_STATS("Net Good Stats", "net-good-stats"),
        UNIQUE("Unique", "unique"),
        SWAGGER("Swagger", "swagger"),
        WHOLE_FIELD_EFFECT("Whole Field Effect", "whole-field-effect"),
        FIELD_EFFECT("Field Effect", "field-effect"),
        ;

        String key;
        String name;

        EffectCategory(String name, String key) {
            this.name = name;
            this.key = key;
        }

        public static EffectCategory fromKey(String key) {
            for (EffectCategory category : values()) {
                if (category.getKey().equals(key)) {
                    return category;
                }
            }
            throw new IllegalArgumentException(key + " category?");
        }

        public String getName() {
            return name;
        }

        public String getKey() {
            return key;
        }
    }

    public enum Category {
        PHYSICAL,
        SPECIAL,
        STATUS,
        ;

        String key;
        String name;

        Category() {
            this.name = Util.toProperCase(toString());
            this.key = toString().toLowerCase();
        }

        public static Category fromKey(String key) {
            for (Category category : values()) {
                if (category.getKey().equals(key)) {
                    return category;
                }
            }
            throw new IllegalArgumentException(key + " category?");
        }

        public String getName() {
            return Util.toProperCase(name);
        }

        public String getKey() {
            return key;
        }
    }

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

    public static class Metadata {
        int healing;
        int minHits;
        int maxHits;
        int minTurns;
        int maxTurns;
        int critRate;
        int drain;
        int ailmentChance;
        int flinchChance;
        int statChance;
        Ailment ailment;
        EffectCategory category;

        public Metadata(DataObject data) {
            this.healing = data.getInt("healing", 0);
            this.minHits = data.getInt("min_hits", 0);
            this.maxHits = data.getInt("max_hits", 0);
            this.minTurns = data.getInt("min_turns", 0);
            this.maxTurns = data.getInt("max_turns", 0);
            this.critRate = data.getInt("crit_rate", 0);
            this.drain = data.getInt("drain", 0);
            this.ailmentChance = data.getInt("ailment_chance", 0);
            this.flinchChance = data.getInt("flinch_chance", 0);
            this.statChance = data.getInt("stat_chance", 0);
            this.ailment = Ailment.fromId(data.getString("ailment"));
            this.category = EffectCategory.fromKey(data.getString("category"));
        }

        public int getHealing() {
            return healing;
        }

        public int getMinHits() {
            return minHits;
        }

        public int getMaxHits() {
            return maxHits;
        }

        public int getMinTurns() {
            return minTurns;
        }

        public int getMaxTurns() {
            return maxTurns;
        }

        public int getCritRate() {
            return critRate;
        }

        public int getDrain() {
            return drain;
        }

        public int getAilmentChance() {
            return ailmentChance;
        }

        public int getFlinchChance() {
            return flinchChance;
        }

        public int getStatChance() {
            return statChance;
        }

        public Ailment getAilment() {
            return ailment;
        }

        public EffectCategory getCategory() {
            return category;
        }

        public String toString() {
            return (healing == 0 ? "" : "**Heal**: " + healing + "%\n") +
                (minHits == 0 ? "" : "**Minimum Hits**: " + minHits + "\n") +
                (maxHits == 0 ? "" : "**Maximum Hits**: " + maxHits + "\n") +
                (minTurns == 0 ? "" : "**Minimum Turns**: " + minTurns + "\n") +
                (maxTurns == 0 ? "" : "**Maximum Turns**: " + maxTurns + "\n") +
                (critRate == 0 ? "" : "**Crit Boost**: +" + critRate + "\n") +
                (drain == 0 ? "" : "**Drain**: " + drain + "%\n") +
                (ailmentChance == 0 ? "" : "**Ailment Chance**: " + ailmentChance + "%\n") +
                (flinchChance == 0 ? "" : "**Flinch Chance**: " + flinchChance + "%\n") +
                (statChance == 0 ? "" : "**Stat Change Chance**: " + statChance + "%\n") +
                "**Ailment Caused**: " + ailment.getName() + "\n" +
                "**Category (Based on Effect)**: " + category.getName();
        }
    }
}
