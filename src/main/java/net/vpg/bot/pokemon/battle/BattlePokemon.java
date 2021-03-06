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
package net.vpg.bot.pokemon.battle;

import net.vpg.bot.pokemon.*;

import java.util.EnumSet;

public class BattlePokemon {
    private final PokemonData pokemonData;
    private final String name;
    private final int maxHP;
    private final BattleStatMapping statChanges = new BattleStatMapping();
    private final EnumSet<Tag> tags = EnumSet.noneOf(Tag.class);
    private StatusCondition status;
    private int currentHP;
    private StatMapping baseStats;
    private Battle.Position position;

    public BattlePokemon(PokemonData pokemon) {
        this(pokemon, pokemon.getMaxHP(), StatusCondition.NONE);
    }

    public BattlePokemon(PlayerPokemon pokemon) {
        this(pokemon, pokemon.getCurrentHP(), pokemon.getStatus());
    }

    private BattlePokemon(PokemonData pokemon, int currentHP, StatusCondition status) {
        this.pokemonData = pokemon;
        this.name = pokemon.getEffectiveName();
        this.maxHP = pokemon.getMaxHP();
        this.currentHP = currentHP;
        this.status = status;
        this.baseStats = pokemon.getStats();
    }

    public PokemonData getPokemonData() {
        return pokemonData;
    }

    public String getName() {
        return name;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public BattleStatMapping getStatChanges() {
        return statChanges;
    }

    public EnumSet<Tag> getTags() {
        return tags;
    }

    public StatusCondition getStatus() {
        return status;
    }

    public void setStatus(StatusCondition status) {
        this.status = status;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public StatMapping getBaseStats() {
        return baseStats;
    }

    public void setBaseStats(StatMapping baseStats) {
        this.baseStats = baseStats;
    }

    public StatMapping getStats() {
        StatMapping stats = new StatMapping(baseStats);
        Stat.forEach(true, stat -> {
            int statChange = statChanges.getStat(stat);
            stats.setStat(stat, stats.getStat(stat) * (2 + Math.max(statChange, 0)) / (2 - (Math.min(statChange, 0))));
        });
        return stats;
    }

    public Battle.Position getPosition() {
        return position;
    }

    public void setPosition(Battle.Position position) {
        this.position = position;
    }

    public enum Tag {

    }
}
