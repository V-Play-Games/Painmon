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
import net.vpg.bot.entities.Pokemon;

import java.util.HashMap;
import java.util.Map;

public class TrainerPokemon extends PokemonData {
    public static final Map<String, TrainerPokemon> CACHE = new HashMap<>();
    protected final String trainerId;
    protected String nickname;

    public TrainerPokemon(DataObject data) {
        this(Pokemon.get(data.getString("base")), data.getString("id"), data);
    }

    public TrainerPokemon(Pokemon base, String id) {
        this(base, id, DataObject.empty()
            .put("id", id)
            .put("base", base.getId()));
    }

    public TrainerPokemon(Pokemon base, String id, DataObject data) {
        super(base, id, data);
        this.trainerId = data.getString("trainerId");
        this.nickname = data.getString("nickname", null);
    }

    public static TrainerPokemon get(String id) {
        return CACHE.get(id);
    }

    @Override
    public Type getDataType() {
        return Type.TRAINER;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        data.put("nickname", nickname);
    }

    public String getEffectiveName() {
        return nickname != null ? nickname : base.getName();
    }
}
