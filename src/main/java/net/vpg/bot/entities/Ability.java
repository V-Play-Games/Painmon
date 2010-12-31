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
import net.vpg.bot.core.Util;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class Ability implements Entity {
    public static final Map<String, Ability> CACHE = new HashMap<>();
    public static final EntityInfo<Ability> INFO = new EntityInfo<>(Ability.class.getResource("ability.json"), Ability::new, CACHE);
    protected final String id;
    protected final String name;
    protected final String effect;
    protected final String description;

    public Ability(DataObject data) {
        this.id = data.getString("name");
        this.name = Util.toProperCase(String.join(" ", id.split("-")));
        this.effect = data.getString("effect");
        this.description = data.getString("description");
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
