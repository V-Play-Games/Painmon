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
import net.vpg.bot.core.Bot;
import net.vpg.bot.core.Range;
import net.vpg.bot.core.Util;
import net.vpg.bot.database.DatabaseObject;
import net.vpg.bot.pokemon.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class PlayablePokemon extends DatabaseObject {
    public static final String COLLECTION_NAME = "pokemon";
    public static final Map<String, PlayablePokemon> CACHE = new HashMap<>();
    public static final EntityInfo<PlayablePokemon> INFO = new EntityInfo<>(COLLECTION_NAME, PlayablePokemon::new, CACHE);
    private static final Range SHINY_RANGE = Range.of(0, 4096);
    private static final Range EV_RANGE = Range.of(0, 31);
    private final Moveset moves;
    private final StatMapping evs;
    private final StatMapping ivs;
    private final Pokemon base;
    private final int playerSpecificId;
    private int slot;
    private String nickname;
    private int level;
    private int exp;
    private boolean shiny;
    private Ability ability;
    private Nature nature;
    private Item heldItem;
    private Gender gender;

    public PlayablePokemon(DataObject data, Bot bot) {
        super(data, bot);
        this.base = Pokemon.get(data.getString("base"));
        this.slot = data.getInt("slot");
        this.playerSpecificId = Integer.parseInt(id.split(":")[0]);
        this.nickname = data.getString("nickname");
        this.level = data.getInt("level");
        this.exp = data.getInt("exp");
        this.shiny = data.getBoolean("shiny");
        this.moves = new Moveset(data.getArray("moves"));
        this.evs = new StatMapping(data.getObject("evs"));
        this.ivs = new StatMapping(data.getObject("ivs"));
        this.ability = Ability.get(data.getString("ability"));
        this.nature = Nature.fromKey(data.getString("nature"));
        this.heldItem = Item.get(data.getString("heldItem"));
        this.gender = Gender.fromKey(data.getInt("gender"));
    }

    public PlayablePokemon(Pokemon base, String id, Bot bot) {
        super(id, bot);
        this.base = base;
        this.moves = new Moveset();
        this.evs = new StatMapping();
        this.ivs = new StatMapping();
        this.playerSpecificId = Integer.parseInt(id.split(":")[0]);
        this.data
            .put("base", base.getId())
            .put("slot", slot)
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

    public static PlayablePokemon get(String id) {
        return id.equals("") ? null : CACHE.get(id);
    }

    public void randomize() {
        setShiny(SHINY_RANGE.random() == 0);
        setNature(Util.getRandom(Nature.values()));
        for (Stat stat : Stat.values()) {
            setIv(stat, EV_RANGE.random());
        }
        Ability[] abilities = getPossibleAbilities()
            .stream()
            .filter(a -> !a.isHidden())
            .map(Pokemon.AbilitySlot::getAbility)
            .toArray(Ability[]::new);
        setAbility(Util.getRandom(abilities));
        setGender(getBase().getSpecies().getGenderRate().generate());
    }

    public int getPlayerSpecificId() {
        return playerSpecificId;
    }

    public List<Pokemon.AbilitySlot> getPossibleAbilities() {
        return base.getAbilities();
    }

    public Pokemon getBase() {
        return base;
    }

    public int getSlot() {
        return slot;
    }

    public PlayablePokemon setSlot(int slot) {
        this.slot = slot;
        update("slot", slot);
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public PlayablePokemon setNickname(String nickname) {
        this.nickname = nickname;
        update("nickname", nickname);
        return this;
    }

    public String getEffectiveName() {
        return nickname != null ? nickname : base.getName();
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

    public Ability getAbility() {
        return ability;
    }

    public PlayablePokemon setAbility(Ability ability) {
        this.ability = ability;
        return this;
    }

    public Moveset getMoves() {
        return moves;
    }

    public PlayablePokemon setMove(int slot, Move move) {
        update("moves", moves.setMove(slot, move));
        return this;
    }

    public StatMapping getEvs() {
        return evs;
    }

    public PlayablePokemon setEv(Stat stat, int ev) {
        this.evs.setStat(stat, ev);
        update("evs", evs);
        return this;
    }

    public StatMapping getIvs() {
        return ivs;
    }

    public PlayablePokemon setIv(Stat stat, int iv) {
        this.ivs.setStat(stat, iv);
        update("ivs", ivs);
        return this;
    }

    public Nature getNature() {
        return nature;
    }

    public PlayablePokemon setNature(Nature nature) {
        this.nature = nature;
        update("nature", nature.toString());
        return this;
    }

    public Item getHeldItem() {
        return heldItem;
    }

    public PlayablePokemon setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
        update("heldItem", heldItem.toString());
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public PlayablePokemon setGender(Gender gender) {
        this.gender = gender;
        update("gender", gender.ordinal());
        return this;
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }
}
