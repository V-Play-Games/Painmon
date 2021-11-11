package net.vpg.bot.entities;

import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.framework.Util;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class Move implements Entity {
    public static final Map<String, Move> CACHE = new HashMap<>();
    protected DataObject data;
    protected int pp;
    protected int accuracy;
    protected String description;
    protected int priority;
    protected Type type;
    protected Target target;
    protected String effect;
    protected String name;
    protected String id;
    protected int effectChance;
    protected int power;
    protected Category category;
    protected Metadata metadata;

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

    public static EntityInfo<Move> getInfo() {
        return new EntityInfo<>(Move.class.getResource("move.json"), Move::new, CACHE);
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
                (minTurns == 0 ? "" : "**Minimum Hits**: " + minTurns + "\n") +
                (maxTurns == 0 ? "" : "**Minimum Hits**: " + maxTurns + "\n") +
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
