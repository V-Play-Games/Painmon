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
package net.vpg.bot.core;

import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.database.DatabaseObject;
import net.vpg.bot.entities.*;
import net.vpg.bot.framework.Bot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class PlayablePokemon extends DatabaseObject {
    public static final String COLLECTION_NAME = "pokemon";
    public static final Map<String, PlayablePokemon> CACHE = new HashMap<>();
    Pokemon base;
    String nickname;
    int level;
    int exp;
    boolean shiny;
    final Moveset moves;
    final DataObject evs;
    final DataObject ivs;
    String nature;
    String heldItem;
    String gender;

    public PlayablePokemon(DataObject data, Bot bot) {
        super(data, bot);
        this.base = Pokemon.get(data.getString("base"));
        this.nickname = data.getString("nickname");
        this.level = data.getInt("level");
        this.exp = data.getInt("exp");
        this.shiny = data.getBoolean("shiny");
        this.moves = new Moveset(data.getArray("moves"));
        this.evs = data.getObject("evs");
        this.ivs = data.getObject("evs");
        this.nature = data.getString("nature");
        this.heldItem = data.getString("heldItem");
        this.gender = data.getString("gender");
    }

    public PlayablePokemon(Pokemon base, String id, Bot bot) {
        super(id, bot);
        this.base = base;
        this.moves = new Moveset();
        this.evs = DataObject.empty();
        this.ivs = DataObject.empty();
        this.data
            .put("nickname", nickname)
            .put("level", level)
            .put("exp", exp)
            .put("shiny", shiny)
            .put("moves", moves)
            .put("evs", evs)
            .put("ivs", ivs)
            .put("nature", nature)
            .put("heldItem", heldItem)
            .put("gender", gender);
    }

    public static EntityInfo<PlayablePokemon> getInfo() {
        return new EntityInfo<>(COLLECTION_NAME, PlayablePokemon::new, CACHE);
    }

    public static PlayablePokemon get(String id) {
        return id.equals("") ? null : CACHE.get(id);
    }

    public List<Pokemon.AbilitySlot> getPossibleAbilities() {
        return base.getAbilities();
    }

    public boolean isDefault() {
        return base.isDefault();
    }

    public String getSpecies() {
        return base.getSpecies();
    }

    public Stats getEvYield() {
        return base.getEvYield();
    }

    public Stats getBaseStats() {
        return base.getBaseStats();
    }

    public Type getType() {
        return base.getType();
    }

    public int getExpYield() {
        return base.getExpYield();
    }

    public List<String> getForms() {
        return base.getForms();
    }

    public String getName() {
        return base.getName();
    }

    public Map<String, List<Pokemon.MoveLearningMethod>> getPossibleMoves() {
        return base.getMoveset();
    }

    public String getNickname() {
        return nickname;
    }

    public PlayablePokemon setNickname(String nickname) {
        this.nickname = nickname;
        update("nickname", nickname);
        return this;
    }

    public int getLevel() {
        return level;
    }

    public PlayablePokemon setLevel(int level) {
        this.level = level;
        update("level", level);
        return this;
    }

    public int getExp() {
        return exp;
    }

    public PlayablePokemon setExp(int exp) {
        this.exp = exp;
        update("exp", exp);
        return this;
    }

    public boolean isShiny() {
        return shiny;
    }

    public PlayablePokemon setShiny(boolean shiny) {
        this.shiny = shiny;
        update("shiny", shiny);
        return this;
    }

    public Moveset getMoves() {
        return moves;
    }

    public PlayablePokemon setMove(int slot, Move move) {
        update("moves", moves.setMove(slot, move));
        return this;
    }

    public DataObject getEvs() {
        return evs;
    }

    public PlayablePokemon setEv(String stat, int ev) {
        this.evs.put(stat, ev);
        update("evs", evs);
        return this;
    }

    public DataObject getIvs() {
        return ivs;
    }

    public PlayablePokemon setIv(String stat, int iv) {
        this.ivs.put(stat, iv);
        update("ivs", ivs);
        return this;
    }

    public String getNature() {
        return nature;
    }

    public PlayablePokemon setNature(String nature) {
        this.nature = nature;
        update("nature", nature);
        return this;
    }

    public String getHeldItem() {
        return heldItem;
    }

    public PlayablePokemon setHeldItem(String heldItem) {
        this.heldItem = heldItem;
        update("heldItem", heldItem);
        return this;
    }

    public String getGender() {
        return gender;
    }

    public PlayablePokemon setGender(String gender) {
        this.gender = gender;
        update("gender", gender);
        return this;
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }
}
