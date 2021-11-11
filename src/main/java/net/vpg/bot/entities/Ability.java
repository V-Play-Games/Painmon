package net.vpg.bot.entities;

import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.framework.Util;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class Ability implements Entity {
    private static EntityInfo<Ability> info;
    public static final Map<String, Ability> CACHE = new HashMap<>();
    String id;
    String name;
    String effect;
    String description;

    public Ability(DataObject data) {
        this.id = data.getString("name");
        this.name = Util.toProperCase(String.join(" ", id.split("-")));
        this.effect = data.getString("effect");
        this.description = data.getString("description");
    }

    public static EntityInfo<Ability> getInfo() {
        return info == null ? info = new EntityInfo<>(Ability.class.getResource("ability.json"), Ability::new, CACHE) : info;
    }

    public static Ability get(String id) {
        return CACHE.get(id);
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEffect() {
        return effect;
    }

    public String getDescription() {
        return description;
    }

    @Nonnull
    @Override
    public DataObject toData() {
        return Entity.super.toData()
            .put("name", name)
            .put("effect", effect)
            .put("description", description);
    }
}
