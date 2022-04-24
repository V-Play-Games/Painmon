package net.vpg.bot.pokemon;

import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.core.Bot;
import net.vpg.bot.database.DatabaseObject;
import net.vpg.bot.entities.EntityInfo;
import net.vpg.bot.entities.Pokemon;

import java.util.HashMap;
import java.util.Map;

public class PlayerPokemon extends TrainerPokemon {
    public static final Map<String, PlayerPokemon> CACHE = new HashMap<>();
    public static final EntityInfo<PlayerPokemon> INFO = new EntityInfo<>("pokemon", PlayerPokemon::new, CACHE);
    protected final DatabaseObject dbObj;
    protected final int playerSpecificId;
    protected int slot;
    protected int exp;
    protected int currentHP;
    protected StatusCondition status;

    public PlayerPokemon(DataObject data, Bot bot) {
        this(Pokemon.get(data.getString("base")), data.getString("id"), data, bot);
    }

    public PlayerPokemon(Pokemon base, String id, Bot bot) {
        this(base, id, DataObject.empty().put("id", id).put("base", base.getId()), bot);
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
        this.slot = data.getInt("slot");
        this.exp = data.getInt("exp");
        this.currentHP = data.getInt("currentHP");
        this.status = StatusCondition.fromKey(data.getInt("status"));
    }

    public static PlayerPokemon get(String id) {
        return CACHE.get(id);
    }

    public DatabaseObject asDatabaseObject() {
        return dbObj;
    }

    public int getPlayerSpecificId() {
        return playerSpecificId;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
        data.put("slot", slot);
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
