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
    private static final Range IV_RANGE = Range.of(0, 31);
    private final Moveset moveset;
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
    private int currentHP;
    private StatusCondition status;

    public PlayablePokemon(DataObject data, Bot bot) {
        super(data, bot);
        this.base = Pokemon.get(data.getString("base"));
        this.slot = data.getInt("slot", 0);
        this.playerSpecificId = Integer.parseInt(id.split(":")[0]);
        this.nickname = data.getString("nickname", null);
        this.level = data.getInt("level", 0);
        this.exp = data.getInt("exp", 0);
        this.shiny = data.getBoolean("shiny", false);
        this.moveset = new Moveset(data.getArray("moves"));
        this.evs = new StatMapping(data.optObject("evs").orElseGet(DataObject::empty));
        this.ivs = new StatMapping(data.optObject("ivs").orElseGet(DataObject::empty));
        this.ability = Ability.get(data.getString("ability", ""));
        this.nature = Nature.fromKey(data.getString("nature", ""));
        this.heldItem = Item.get(data.getString("heldItem", ""));
        this.gender = Gender.fromKey(data.getInt("gender", -1));
        this.currentHP = data.getInt("currentHP", getStats().getHP());
        this.status = StatusCondition.fromKey(data.getInt("status", -1));
    }

    public PlayablePokemon(Pokemon base, String id, Bot bot) {
        super(id, bot);
        this.base = base;
        this.moveset = new Moveset();
        this.evs = new StatMapping();
        this.ivs = new StatMapping();
        this.playerSpecificId = Integer.parseInt(id.split(":")[0]);
        this.currentHP = getStats().getHP();
        this.status = StatusCondition.NONE;
        this.data
            .put("base", base.getId())
            .put("slot", slot)
            .put("nickname", nickname)
            .put("level", level)
            .put("exp", exp)
            .put("shiny", shiny)
            .put("moves", moveset)
            .put("evs", evs)
            .put("ivs", ivs)
            .put("ability", "")
            .put("nature", "")
            .put("heldItem", "")
            .put("gender", -1)
            .put("currentHP", currentHP)
            .put("status", status.ordinal());
    }

    public static PlayablePokemon get(String id) {
        return id.equals("") ? null : CACHE.get(id);
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

    public void setSlot(int slot) {
        this.slot = slot;
        update("slot", slot);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        update("nickname", nickname);
    }

    public String getEffectiveName() {
        return nickname != null ? nickname : base.getName();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        update("level", level);
    }

    public void setLevelAccordingToExp() {
        setLevel(base.getSpecies().getGrowthRate().getLevelAtExp(exp));
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
        update("exp", exp);
    }

    public void setExpAccordingToLevel() {
        setExp(base.getSpecies().getGrowthRate().getExpAtLevel(level));
    }

    public boolean isShiny() {
        return shiny;
    }

    public void setShiny(boolean shiny) {
        this.shiny = shiny;
        update("shiny", shiny);
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
        update("ability", ability.getId());
    }

    public Moveset getMoveset() {
        return moveset;
    }

    public StatMapping getEvs() {
        return evs;
    }

    public StatMapping getIvs() {
        return ivs;
    }

    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
        update("nature", nature.name());
    }

    public Item getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
        update("heldItem", heldItem.getId());
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        update("gender", gender.ordinal());
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
        update("currentHP", currentHP);
    }

    public StatusCondition getStatus() {
        return status;
    }

    public void setStatus(StatusCondition status) {
        this.status = status;
        update("status", status.ordinal());
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    public void randomize() {
        setShiny(SHINY_RANGE.random() == 0);
        setNature(Util.getRandom(Nature.values()));
        for (Stat stat : Stat.values()) {
            getIvs().setStat(stat, IV_RANGE.random());
        }
        Ability[] abilities = getPossibleAbilities()
            .stream()
            .filter(a -> !a.isHidden())
            .map(Pokemon.AbilitySlot::getAbility)
            .toArray(Ability[]::new);
        setAbility(Util.getRandom(abilities));
        setGender(base.getSpecies().getGenderRate().generate());
    }

    public StatMapping getStats() {
        StatMapping stats = new StatMapping(base.getBaseStats());
        Stat.forEach(true, stat -> {
            int value = (2 * stats.getStat(stat) + ivs.getHP() + (evs.getHP() / 4)) * level / 100;
            if (stat == Stat.HP) {
                if (stats.getHP() != 1) {
                    stats.setHP(value + level + 10);
                }
            } else {
                stats.setStat(stat, (int) ((value + 5) * nature.getMultiplierForStat(stat)));
            }
        });
        return stats;
    }
}
