package net.vpg.bot.pokemon;

import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.core.Bot;
import net.vpg.bot.core.Range;
import net.vpg.bot.core.Util;
import net.vpg.bot.entities.*;
import net.vpg.bot.pokemon.battle.BattlePokemon;

import javax.annotation.Nonnull;

public abstract class PokemonData implements Entity {
    public static final Range SHINY_RANGE = Range.of(0, 4096);
    public static final Range IV_RANGE = Range.of(0, 31);
    protected final DataObject data;
    protected final String id;
    protected final Pokemon base;
    protected final Moveset moveset;
    protected final StatMapping evs;
    protected final StatMapping ivs;
    protected int level;
    protected boolean shiny;
    protected Ability ability;
    protected Nature nature;
    protected Item heldItem;
    protected Gender gender;

    public PokemonData(DataObject data) {
        this(Pokemon.get(data.getString("base")), data.getString("id"), data);
    }

    public PokemonData(Pokemon base, String id) {
        this(base, id, DataObject.empty()
            .put("id", id)
            .put("base", base.getId()));
    }

    public PokemonData(Pokemon base, String id, DataObject data) {
        this.data = data;
        this.base = base;
        this.id = id;
        this.moveset = new Moveset(data.getArray("moves"));
        this.evs = new StatMapping(data.optObject("evs").orElseGet(DataObject::empty));
        this.ivs = new StatMapping(data.optObject("ivs").orElseGet(DataObject::empty));
        this.level = data.getInt("level", 1);
        this.shiny = data.getBoolean("shiny", false);
        this.ability = Ability.get(data.getString("ability", ""));
        this.nature = Nature.fromKey(data.getString("nature", ""));
        this.heldItem = Item.get(data.getString("heldItem", ""));
        this.gender = Gender.fromKey(data.getInt("gender", 0));
        data.put("evs", evs).put("ivs", ivs);
    }

    public BattlePokemon prepareForBattle() {
        return new BattlePokemon(this);
    }

    public abstract Type getDataType();

    @Override
    public String getId() {
        return id;
    }

    public Pokemon getBase() {
        return base;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
        data.put("level", level);
    }

    public boolean isShiny() {
        return shiny;
    }

    public void setShiny(boolean shiny) {
        this.shiny = shiny;
        data.put("shiny", shiny);
    }

    public Ability getAbility() {
        return ability;
    }

    public void setAbility(Ability ability) {
        this.ability = ability;
        data.put("ability", ability.getId());
    }

    public Nature getNature() {
        return nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
        data.put("nature", nature.name());
    }

    public Item getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
        data.put("heldItem", heldItem.getId());
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        data.put("gender", gender.ordinal());
    }

    @Override
    @Nonnull
    public DataObject toData() {
        return data;
    }

    public String getEffectiveName() {
        return base.getName();
    }

    public int getMaxHP() {
        int baseHP = base.getBaseStats().getHP();
        return baseHP == 1 ? 1 : (2 * baseHP + ivs.getHP() + (evs.getHP() / 4)) * level / 100 + level + 10;
    }

    public StatMapping getStats() {
        StatMapping stats = new StatMapping(base.getBaseStats());
        Stat.forEach(true, stat -> {
            if (stat != Stat.HP) {
                stats.setStat(stat,
                    (int) (((2 * stats.getStat(stat) + ivs.getHP() + (evs.getHP() / 4)) * level / 100 + 5)
                        * nature.getMultiplierForStat(stat)));
            }
        });
        stats.setHP(getMaxHP());
        return stats;
    }

    public void randomize() {
        setShiny(SHINY_RANGE.random() == 0);
        setNature(Util.getRandom(Nature.values()));
        Stat.forEach(true, stat -> ivs.setStat(stat, IV_RANGE.random()));
        Ability[] abilities = getBase().getAbilities()
            .stream()
            .filter(a -> !a.isHidden())
            .map(Pokemon.AbilitySlot::getAbility)
            .toArray(Ability[]::new);
        setAbility(Util.getRandom(abilities));
        setGender(base.getSpecies().getGenderRate().generate());
    }

    public PlayerPokemon giveTo(Player player, String id, Bot bot) {
        DataObject data = DataObject.fromETF(this.data.toETF())
            .put("id", id == null ? this.id : id)
            .put("playerSpecificId", player.getNextPokemonId())
            .put("trainerId", player.getId())
            .remove("slot")
            .remove("currentHP")
            .remove("status");
        data.getArray("moves").stream(DataArray::getObject).forEach(move -> move.remove("currentPP"));
        PlayerPokemon pokemon = new PlayerPokemon(data, bot);
        pokemon.setExpAccordingToLevel();
        return pokemon;
    }

    public enum Type {
        WILD, TRAINER, PLAYER
    }
}
