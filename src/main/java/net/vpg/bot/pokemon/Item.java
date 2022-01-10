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
import net.vpg.bot.entities.Entity;
import net.vpg.bot.entities.EntityInfo;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class Item implements Entity {
    private static final Map<String, Item> CACHE = new HashMap<>();
    private static final EntityInfo<Item> INFO = new EntityInfo<>(Entity.class.getResource("items.json"), Item::new, CACHE);
    private final DataObject data;
    private final String id;

    public Item(DataObject data) {
        this.data = data;
        this.id = data.getString("id");
    }

    public static EntityInfo<Item> getInfo() {
        return INFO;
    }

    public static Item get(String id) {
        return CACHE.get(id);
    }

    @Override
    public String getId() {
        return id;
    }

    @Nonnull
    @Override
    public DataObject toData() {
        return data;
    }
}
