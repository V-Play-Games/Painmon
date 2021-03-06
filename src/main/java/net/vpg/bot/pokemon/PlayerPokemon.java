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
import net.vpg.bot.core.Bot;
import net.vpg.bot.database.DatabaseObject;
import net.vpg.bot.entities.EntityInfo;
import net.vpg.bot.entities.Player;
import net.vpg.bot.entities.Pokemon;

import java.util.HashMap;
import java.util.Map;

public class PlayerPokemon extends TrainerPokemon {
    public static final Map<String, PlayerPokemon> CACHE = new HashMap<>();
    public static final EntityInfo<PlayerPokemon> INFO = new EntityInfo<>("pokemon", PlayerPokemon::new, CACHE);
    protected final DatabaseObject dbObj;
    protected final int playerSpecificId;
    protected int exp;
    protected int currentHP;
    protected StatusCondition status;

    public PlayerPokemon(DataObject data, Bot bot) {
        this(Pokemon.get(data.getString("base")), data.getString("id"), data, bot);
    }

    public PlayerPokemon(Pokemon base, String id, Player player, Bot bot) {
        this(base, id, DataObject.empty()
            .put("id", id)
            .put("base", base.getId())
            .put("playerSpecificId", player.getNextPokemonId())
            .put("trainerId", player.getId()), bot);
    }

    public PlayerPokemon(Pokemon base, String id, DataObject data, Bot bot) {
        super(base, id, data);
        this.dbObj = new DatabaseObject(data, bot) {
            @Override
            public String getCollectionName() {
                return "pokemon";
            }
        };
        this.playerSpecificId = data.getInt("playerSpecificId");
        if (!data.isNull("exp")) {
            setExpAccordingToLevel();
        } else {
            this.exp = data.getInt("exp", 0);
        }
        this.currentHP = data.getInt("currentHP", getMaxHP());
        this.status = StatusCondition.fromKey(data.getInt("status", 0));
    }

    public static PlayerPokemon get(String id) {
        return CACHE.get(id);
    }

    public static PlayerPokemon createNew(DataObject data, Bot bot) {
        PlayerPokemon pokemon = new PlayerPokemon(data, bot);
        pokemon.asDatabaseObject().ensureInserted();
        CACHE.put(pokemon.getId(), pokemon);
        return pokemon;
    }

    @Override
    public Type getDataType() {
        return Type.PLAYER;
    }

    public DatabaseObject asDatabaseObject() {
        return dbObj;
    }

    public int getPlayerSpecificId() {
        return playerSpecificId;
    }

    public void setLevelAccordingToExp() {
        setLevel(base.getSpecies().getGrowthRate().getLevelAtExp(exp));
    }

    public void setExpAccordingToLevel() {
        setExp(base.getSpecies().getGrowthRate().getExpAtLevel(level));
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
        data.put("exp", exp);
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
        data.put("currentHP", currentHP);
    }

    public StatusCondition getStatus() {
        return status;
    }

    public void setStatus(StatusCondition status) {
        this.status = status;
        data.put("status", status.ordinal());
    }
}
