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
package net.vpg.bot.core;

import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.SerializableArray;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlayerTeam implements SerializableArray {
    final DataArray data;
    final List<PlayablePokemon> cache;

    public PlayerTeam(DataArray data) {
        this.data = data;
        this.cache = data.stream(DataArray::getString).map(PlayablePokemon::get).collect(Collectors.toList());
    }

    public PlayerTeam() {
        this.data = DataArray.empty();
        this.cache = new ArrayList<>();
    }

    @Nonnull
    @Override
    public DataArray toDataArray() {
        return data;
    }
}
