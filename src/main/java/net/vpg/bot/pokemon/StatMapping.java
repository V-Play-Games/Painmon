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

public class StatMapping {
    private int hp;
    private int attack;
    private int defense;
    private int spAtk;
    private int spDef;
    private int speed;

    public StatMapping(DataObject data) {
        this(data.getInt(Stat.HP.KEY),
            data.getInt(Stat.ATTACK.KEY),
            data.getInt(Stat.DEFENSE.KEY),
            data.getInt(Stat.SP_ATK.KEY),
            data.getInt(Stat.SP_DEF.KEY),
            data.getInt(Stat.SPEED.KEY));
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
        }
        return -1;
    }

    public void setStat(Stat stat, int value) {
        switch (stat) {
            case HP:
                hp = value;
                break;
            case ATTACK:
                attack = value;
                break;
            case DEFENSE:
                defense = value;
                break;
            case SP_ATK:
                spAtk = value;
                break;
            case SP_DEF:
                spDef = value;
                break;
            case SPEED:
                speed = value;
                break;
        }
    }

    public String toString() {
        return "HP: " + hp + " | Attack: " + attack + " | Defense: " + defense + " | Sp. Atk: " + spAtk + " | Sp. Def: " + spDef + " | Speed: " + speed;
    }
}
