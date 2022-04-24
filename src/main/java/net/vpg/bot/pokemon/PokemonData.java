package net.vpg.bot.pokemon;

import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.core.Range;
import net.vpg.bot.core.Util;
import net.vpg.bot.entities.Ability;
import net.vpg.bot.entities.Entity;
import net.vpg.bot.entities.Item;
import net.vpg.bot.entities.Pokemon;

import javax.annotation.Nonnull;

public class PokemonData implements Entity {
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
        setLevel(data.getInt("level", 0));
        setShiny(data.getBoolean("shiny", false));
        setAbility(Ability.get(data.getString("ability", "")));
        setNature(Nature.fromKey(data.getString("nature", "")));
        setHeldItem(Item.get(data.getString("heldItem", "")));
        setGender(Gender.fromKey(data.getInt("gender", 0)));
        data.put("moveset", moveset)
            .put("evs", evs)
            .put("ivs", ivs);
    }

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

    public void randomize() {
        setShiny(SHINY_RANGE.random() == 0);
        setNature(Util.getRandom(Nature.values()));
        for (Stat stat : Stat.values()) {
            getIvs().setStat(stat, IV_RANGE.random());
        }
        Ability[] abilities = getBase().getAbilities()
            .stream()
            .filter(a -> !a.isHidden())
            .map(Pokemon.AbilitySlot::getAbility)
            .toArray(Ability[]::new);
        setAbility(Util.getRandom(abilities));
        setGender(base.getSpecies().getGenderRate().generate());
    }
}
