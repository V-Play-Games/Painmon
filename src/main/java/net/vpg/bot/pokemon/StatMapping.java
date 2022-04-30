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

import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.api.utils.data.SerializableData;

import javax.annotation.Nonnull;
import java.util.Map;

import static net.vpg.bot.pokemon.Stat.*;

public class StatMapping implements SerializableData {
    protected int hp;
    protected int attack;
    protected int defense;
    protected int spAtk;
    protected int spDef;
    protected int speed;

    public StatMapping(StatMapping stats) {
        this(stats.hp, stats.attack, stats.defense, stats.spAtk, stats.spDef, stats.speed);
    }

    public StatMapping(DataObject data) {
        this(data.getInt(HP.key, 0),
            data.getInt(ATTACK.key, 0),
            data.getInt(DEFENSE.key, 0),
            data.getInt(SP_ATK.key, 0),
            data.getInt(SP_DEF.key, 0),
            data.getInt(SPEED.key, 0));
    }

    public StatMapping(int hp, int attack, int defense, int spAtk, int spDef, int speed) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spAtk = spAtk;
        this.spDef = spDef;
        this.speed = speed;
    }

    public StatMapping() {
        this(0, 0, 0, 0, 0, 0);
    }

    public int getHP() {
        return hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getSpAtk() {
        return spAtk;
    }

    public void setSpAtk(int spAtk) {
        this.spAtk = spAtk;
    }

    public int getSpDef() {
        return spDef;
    }

    public void setSpDef(int spDef) {
        this.spDef = spDef;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getStat(Stat stat) {
        switch (stat) {
            case HP:
                return hp;
            case ATTACK:
                return attack;
            case DEFENSE:
                return defense;
            case SP_ATK:
                return spAtk;
            case SP_DEF:
                return spDef;
            case SPEED:
                return speed;
            default:
                throw new IllegalArgumentException("trying to get battle stats in permanent stat mapping");
        }
    }

    public void setStat(Stat stat, int value) {
        switch (stat) {
            case HP:
                setHP(value);
                break;
            case ATTACK:
                setAttack(value);
                break;
            case DEFENSE:
                setDefense(value);
                break;
            case SP_ATK:
                setSpAtk(value);
                break;
            case SP_DEF:
                setSpDef(value);
                break;
            case SPEED:
                setSpeed(value);
                break;
            default:
                throw new IllegalArgumentException("Setting battle stats in permanent stat mapping");
        }
    }

    public Map<Stat, Integer> toMap() {
        return Map.of(
            HP, hp,
            ATTACK, attack,
            DEFENSE, defense,
            SP_ATK, spAtk,
            SP_DEF, spDef,
            SPEED, speed
        );
    }

    public void copyFrom(StatMapping stats) {
        this.hp = stats.hp;
        this.attack = stats.attack;
        this.defense = stats.defense;
        this.spAtk = stats.spAtk;
        this.spDef = stats.spDef;
        this.speed = stats.speed;
    }

    public String toString() {
        return "HP: " + hp + " | Attack: " + attack + " | Defense: " + defense + " | Sp. Atk: " + spAtk + " | Sp. Def: " + spDef + " | Speed: " + speed;
    }

    @Nonnull
    @Override
    public DataObject toData() {
        DataObject tor = DataObject.empty();
        toMap().forEach((stat, value) -> tor.put(stat.getKey(), value));
        return tor;
    }
}
