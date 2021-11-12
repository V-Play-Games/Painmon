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

public class Stats {
    int hp;
    int attack;
    int defense;
    int spAtk;
    int spDef;
    int speed;

    public Stats(DataObject data) {
        this(data.getInt("hp"),
            data.getInt("attack"),
            data.getInt("defense"),
            data.getInt("special-attack"),
            data.getInt("special-defense"),
            data.getInt("speed"));
    }

    public Stats(int hp, int attack, int defense, int spAtk, int spDef, int speed) {
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spAtk = spAtk;
        this.spDef = spDef;
        this.speed = speed;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpAtk() {
        return spAtk;
    }

    public int getSpDef() {
        return spDef;
    }

    public int getSpeed() {
        return speed;
    }

    public String toString() {
        return "HP: " + hp + " | Attack: " + attack + " | Defense: " + defense + " | Sp. Atk: " + spAtk + " | Sp. Def: " + spDef + " | Speed: " + speed;
    }
}
