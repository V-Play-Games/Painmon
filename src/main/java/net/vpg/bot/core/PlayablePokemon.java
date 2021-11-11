package net.vpg.bot.core;

import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.database.DatabaseObject;
import net.vpg.bot.entities.*;
import net.vpg.bot.framework.Bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class PlayablePokemon extends DatabaseObject {
    public static final String COLLECTION_NAME = "pokemon";
    public static final Map<String, PlayablePokemon> CACHE = new HashMap<>();
    Pokemon base;
    int slot;
    String nickname;
    int level;
    int exp;
    boolean shiny;
    List<PlayableMove> moves;
    DataObject evs;
    DataObject ivs;
    String nature;
    String heldItem;
    String gender;

    public PlayablePokemon(DataObject data, Bot bot) {
        super(data, bot);
        this.base = Pokemon.get(data.getString("base"));
        this.slot = data.getInt("slot");
        this.nickname = data.getString("nickname");
        this.level = data.getInt("level");
        this.exp = data.getInt("exp");
        this.shiny = data.getBoolean("shiny");
        this.moves = data.getArray("moves").stream(DataArray::getObject).map(PlayableMove::new).collect(Collectors.toList());
        this.evs = data.getObject("evs");
        this.ivs = data.getObject("evs");
        this.nature = data.getString("nature");
        this.heldItem = data.getString("heldItem");
        this.gender = data.getString("gender");
    }

    public PlayablePokemon(Pokemon base, String id, Bot bot) {
        super(id, bot);
        this.base = base;
        this.moves = new ArrayList<>();
        this.evs = DataObject.empty();
        this.ivs = DataObject.empty();
        this.data
            .put("slot", slot)
            .put("nickname", nickname)
            .put("level", level)
            .put("exp", exp)
            .put("shiny", shiny)
            .put("moves", DataArray.empty().addAll(moves))
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
        return CACHE.get(id);
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

    public List<PlayableMove> getMoves() {
        return moves;
    }

    public PlayablePokemon setMoves(List<PlayableMove> moves) {
        this.moves = moves;
        update("moves", moves);
        return this;
    }

    public PlayablePokemon setMove(int slot, Move move) {
        this.moves.add(slot - 1, new PlayableMove(move, slot));
        update("slot", slot);
        return this;
    }

    public PlayablePokemon setMove(int slot, PlayableMove move) {
        this.moves.add(slot - 1, move);
        update("slot", slot);
        return this;
    }

    public DataObject getEvs() {
        return evs;
    }

    public PlayablePokemon setEvs(DataObject evs) {
        this.evs = evs;
        update("slot", slot);
        return this;
    }

    public PlayablePokemon setEv(String stat, int ev) {
        this.evs.put(stat, ev);
        update("slot", slot);
        return this;
    }

    public DataObject getIvs() {
        return ivs;
    }

    public PlayablePokemon setIvs(DataObject ivs) {
        this.ivs = ivs;
        update("slot", slot);
        return this;
    }

    public PlayablePokemon setIv(String stat, int iv) {
        this.ivs.put(stat, iv);
        update("slot", slot);
        return this;
    }

    public String getNature() {
        return nature;
    }

    public PlayablePokemon setNature(String nature) {
        this.nature = nature;
        update("slot", slot);
        return this;
    }

    public String getHeldItem() {
        return heldItem;
    }

    public PlayablePokemon setHeldItem(String heldItem) {
        this.heldItem = heldItem;
        update("slot", slot);
        return this;
    }

    public String getGender() {
        return gender;
    }

    public PlayablePokemon setGender(String gender) {
        this.gender = gender;
        update("slot", slot);
        return this;
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }
}
