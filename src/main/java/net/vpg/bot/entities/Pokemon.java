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

import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.core.Util;
import net.vpg.bot.pokemon.StatMapping;
import net.vpg.bot.pokemon.Type;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Pokemon implements Entity {
    public static final Map<String, Pokemon> CACHE = new HashMap<>();
    public static final EntityInfo<Pokemon> INFO = new EntityInfo<>(Pokemon.class.getResource("pokemon.json"), Pokemon::new, CACHE);
    private final List<AbilitySlot> abilities;
    private final boolean isDefault;
    private final EntityReference<PokemonSpecies> species;
    private final StatMapping evYield;
    private final StatMapping baseStats;
    private final Type type;
    private final String id;
    private final int expYield;
    private final List<String> forms;
    private final String name;
    private final Map<String, List<MoveLearningMethod>> moveset;

    public Pokemon(DataObject data) {
        abilities = data.getArray("abilities")
            .stream(DataArray::getObject)
            .map(AbilitySlot::new)
            .collect(Collectors.toList());
        isDefault = data.getBoolean("default");
        species = new EntityReference<>(PokemonSpecies.INFO, data.getString("species"));
        evYield = new StatMapping(data.getObject("ev_yield"));
        baseStats = new StatMapping(data.getObject("stats"));
        type = Type.fromId(data.getString("type"));
        id = data.getString("name");
        name = Util.toProperCase(String.join(" ", id.split("-")));
        expYield = data.getInt("exp");
        forms = data.getArray("forms").stream(DataArray::getString).collect(Collectors.toList());
        moveset = data.getObject("moves")
            .toMap()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> Arrays.stream(e.getValue().toString().split(";;;"))
                .map(MoveLearningMethod::of)
                .collect(Collectors.toList())));
    }

    public static Pokemon get(String id) {
        return CACHE.get(id);
    }

    public List<AbilitySlot> getAbilities() {
        return abilities;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public PokemonSpecies getSpecies() {
        return species.get();
    }

    public StatMapping getEvYield() {
        return evYield;
    }

    public StatMapping getBaseStats() {
        return baseStats;
    }

    public Type getType() {
        return type;
    }

    public int getExpYield() {
        return expYield;
    }

    public List<String> getForms() {
        return forms;
    }

    public String getName() {
        return name;
    }

    public Map<String, List<MoveLearningMethod>> getMoveset() {
        return moveset;
    }

    @Override
    public String getId() {
        return id;
    }

    public static class AbilitySlot {
        private final boolean hidden;
        private final EntityReference<Ability> reference;
        private final int slot;

        public AbilitySlot(DataObject data) {
            this(data.getBoolean("is_hidden"), data.getString("ability"), data.getInt("slot"));
        }

        public AbilitySlot(boolean hidden, String abilityId, int slot) {
            this.hidden = hidden;
            this.reference = new EntityReference<>(Ability.INFO, abilityId);
            this.slot = slot;
        }

        public String getId() {
            return getAbility().getId();
        }

        public Ability getAbility() {
            return reference.get();
        }

        public boolean isHidden() {
            return hidden;
        }

        public int getSlot() {
            return slot;
        }
    }

    public static class MoveLearningMethod {
        private static final Map<String, MoveLearningMethod> METHODS = new HashMap<>();

        static {
            for (int i = 1; i <= 100; i++) {
                METHODS.put("level-up;" + i, new MoveLearningMethod("level-up", i, 0));
            }
            METHODS.put("level-up", new MoveLearningMethod("level-up", 0, 0));
            METHODS.put("machine", new MoveLearningMethod("machine", 0, 1));
            METHODS.put("egg", new MoveLearningMethod("egg", 0, 2));
            METHODS.put("tutor", new MoveLearningMethod("tutor", 0, 3));
            METHODS.put("form-change", new MoveLearningMethod("form-change", 0, 4));
            METHODS.put("light-ball-egg", new MoveLearningMethod("light-ball-egg", 0, 5));
        }

        private final String method;
        private final int levelRequired;
        private final int orderingKey;

        MoveLearningMethod(String method, int levelRequired, int orderingKey) {
            this.method = method;
            this.levelRequired = levelRequired;
            this.orderingKey = orderingKey;
        }

        public static MoveLearningMethod of(String id) {
            return METHODS.get(id);
        }

        public String getMethod() {
            return method;
        }

        public String getOrderingKey() {
            return orderingKey + ";" + (levelRequired < 10 ? "0" : "") + levelRequired;
        }

        public boolean isLevelUp() {
            return method.equals("level-up");
        }

        public int getLevelRequired() {
            return levelRequired;
        }

        @Override
        public String toString() {
            switch (method) {
                case "level-up":
                    return "at level " + levelRequired;
                case "machine":
                    return "using TM/HM";
                case "egg":
                    return "by breeding";
                case "tutor":
                    return "by move tutor";
                case "form-change":
                    return "after changing form";
                case "light-ball-egg":
                    return "using what?";
                default:
                    return "by no idea how";
            }
        }
    }
}
