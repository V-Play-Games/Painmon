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

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.stream.Collectors;

public class Bag implements SerializableData {
    private final DataObject data;

    public Bag() {
        this.data = DataObject.empty();
    }

    public Bag(DataObject data) {
        this.data = data;
    }

    public Map<String, Integer> getItems() {
        return data.toMap()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> (Integer) e.getValue()));
    }

    public void incrementItemCount(String id) {
        addItemCount(id, 1);
    }

    public void decrementItemCount(String id) {
        addItemCount(id, -1);
    }

    public void addItemCount(String id, int delta) {
        setItemCount(id, data.getInt(id, 0) + delta);
    }

    public void setItemCount(String id, int value) {
        data.put(id, value);
    }

    @Nonnull
    @Override
    public DataObject toData() {
        return data;
    }
}
