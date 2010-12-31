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
import net.dv8tion.jda.api.utils.data.SerializableData;
import net.vpg.bot.entities.Item;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.stream.Collectors;

public class Bag implements SerializableData {
    private final DataObject data;
    private final Map<Item, Integer> items;

    public Bag(DataObject data) {
        this.data = data;
        items = data.toMap()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(e -> Item.get(e.getKey()), e -> (Integer) e.getValue()));
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void incrementItemCount(String id) {
        addItemCount(id, 1);
    }

    public void decrementItemCount(String id) {
        addItemCount(id, -1);
    }

    public void addItemCount(String id, int delta) {
        Item item = Item.get(id);
        int count = items.getOrDefault(item, 0);
        items.put(item, count + delta);
    }

    public void setItemCount(String id, int value) {
        items.put(Item.get(id), value);
    }

    @Nonnull
    @Override
    public DataObject toData() {
        return data;
    }
}
