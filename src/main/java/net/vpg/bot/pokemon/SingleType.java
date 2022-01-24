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
package net.vpg.bot.pokemon;

import net.vpg.bot.core.MiscUtil;
import net.vpg.bot.core.Util;

import java.util.List;
import java.util.Map;

import static net.vpg.bot.pokemon.Type.Multiplier.*;

public enum SingleType implements Type {
    NORMAL(new Matchup.Builder()
        .put("rock", RESISTANT)
        .put("ghost", IMMUNE)
        .put("steel", RESISTANT)),
    FIRE(new Matchup.Builder()
        .put("fire", RESISTANT)
        .put("water", RESISTANT)
        .put("grass", EFFECTIVE)
        .put("ice", EFFECTIVE)
        .put("bug", EFFECTIVE)
        .put("rock", RESISTANT)
        .put("dragon", RESISTANT)
        .put("steel", EFFECTIVE)),
    WATER(new Matchup.Builder()
        .put("fire", EFFECTIVE)
        .put("water", RESISTANT)
        .put("grass", RESISTANT)
        .put("ground", EFFECTIVE)
        .put("rock", EFFECTIVE)
        .put("dragon", RESISTANT)),
    ELECTRIC(new Matchup.Builder()
        .put("water", EFFECTIVE)
        .put("electric", RESISTANT)
        .put("grass", RESISTANT)
        .put("ground", IMMUNE)
        .put("flying", EFFECTIVE)
        .put("dragon", RESISTANT)),
    GRASS(new Matchup.Builder()
        .put("fire", RESISTANT)
        .put("water", EFFECTIVE)
        .put("grass", RESISTANT)
        .put("poison", RESISTANT)
        .put("ground", EFFECTIVE)
        .put("flying", RESISTANT)
        .put("bug", RESISTANT)
        .put("rock", EFFECTIVE)
        .put("dragon", RESISTANT)
        .put("steel", RESISTANT)),
    ICE(new Matchup.Builder()
        .put("fire", RESISTANT)
        .put("water", RESISTANT)
        .put("grass", EFFECTIVE)
        .put("ice", RESISTANT)
        .put("ground", EFFECTIVE)
        .put("flying", EFFECTIVE)
        .put("dragon", EFFECTIVE)
        .put("steel", RESISTANT)),
    FIGHTING(new Matchup.Builder()
        .put("normal", EFFECTIVE)
        .put("ice", EFFECTIVE)
        .put("poison", RESISTANT)
        .put("flying", RESISTANT)
        .put("psychic", RESISTANT)
        .put("bug", RESISTANT)
        .put("rock", EFFECTIVE)
        .put("ghost", IMMUNE)
        .put("dark", EFFECTIVE)
        .put("steel", EFFECTIVE)
        .put("fairy", RESISTANT)),
    POISON(new Matchup.Builder()
        .put("grass", EFFECTIVE)
        .put("poison", RESISTANT)
        .put("ground", RESISTANT)
        .put("rock", RESISTANT)
        .put("ghost", RESISTANT)
        .put("steel", IMMUNE)
        .put("fairy", EFFECTIVE)),
    GROUND(new Matchup.Builder()
        .put("fire", EFFECTIVE)
        .put("electric", EFFECTIVE)
        .put("grass", RESISTANT)
        .put("poison", EFFECTIVE)
        .put("flying", IMMUNE)
        .put("bug", RESISTANT)
        .put("rock", EFFECTIVE)
        .put("steel", EFFECTIVE)),
    FLYING(new Matchup.Builder()
        .put("electric", RESISTANT)
        .put("grass", EFFECTIVE)
        .put("fighting", EFFECTIVE)
        .put("bug", EFFECTIVE)
        .put("rock", RESISTANT)
        .put("steel", RESISTANT)),
    PSYCHIC(new Matchup.Builder()
        .put("fighting", EFFECTIVE)
        .put("poison", EFFECTIVE)
        .put("psychic", RESISTANT)
        .put("dark", IMMUNE)
        .put("steel", RESISTANT)),
    BUG(new Matchup.Builder()
        .put("fire", RESISTANT)
        .put("grass", EFFECTIVE)
        .put("fighting", RESISTANT)
        .put("poison", RESISTANT)
        .put("flying", RESISTANT)
        .put("psychic", EFFECTIVE)
        .put("ghost", RESISTANT)
        .put("dark", EFFECTIVE)
        .put("steel", RESISTANT)
        .put("fairy", RESISTANT)),
    ROCK(new Matchup.Builder()
        .put("fire", EFFECTIVE)
        .put("ice", EFFECTIVE)
        .put("fighting", RESISTANT)
        .put("ground", RESISTANT)
        .put("flying", EFFECTIVE)
        .put("bug", EFFECTIVE)
        .put("steel", RESISTANT)),
    GHOST(new Matchup.Builder()
        .put("normal", IMMUNE)
        .put("psychic", EFFECTIVE)
        .put("ghost", EFFECTIVE)
        .put("dark", RESISTANT)),
    DRAGON(new Matchup.Builder()
        .put("dragon", EFFECTIVE)
        .put("dark", RESISTANT)
        .put("fairy", IMMUNE)),
    DARK(new Matchup.Builder()
        .put("fighting", RESISTANT)
        .put("psychic", EFFECTIVE)
        .put("ghost", EFFECTIVE)
        .put("dark", RESISTANT)
        .put("fairy", RESISTANT)),
    STEEL(new Matchup.Builder()
        .put("fire", RESISTANT)
        .put("water", RESISTANT)
        .put("electric", RESISTANT)
        .put("ice", EFFECTIVE)
        .put("rock", EFFECTIVE)
        .put("steel", RESISTANT)
        .put("fairy", EFFECTIVE)),
    FAIRY(new Matchup.Builder()
        .put("fire", RESISTANT)
        .put("fighting", EFFECTIVE)
        .put("poison", RESISTANT)
        .put("dragon", EFFECTIVE)
        .put("dark", EFFECTIVE)
        .put("steel", RESISTANT));
    private static final Map<String, SingleType> TYPES = MiscUtil.getEnumMap(SingleType.class);
    private final Matchup matchup;
    private List<Type> immune;
    private List<Type> effective;

    SingleType(Matchup.Builder builder) {
        this.matchup = builder.build();
    }

    public static SingleType fromId(String id) {
        return TYPES.get(id);
    }

    @Override
    public String getName() {
        return Util.toProperCase(toString());
    }

    @Override
    public String getId() {
        return toString().toLowerCase();
    }

    @Override
    public List<Type> immuneAgainst() {
        return immune == null ? immune = matchup.filter(IMMUNE) : immune;
    }

    @Override
    public List<Type> effectiveAgainst() {
        return effective == null ? effective = matchup.filter(m -> m.getValue() > 0) : effective;
    }

    @Override
    public Matchup getMatchup() {
        return matchup;
    }

    @Override
    public double multiplierReceivingDamage(Type type) {
        return type.getMatchup().multiplierAgainst(getId());
    }
}
