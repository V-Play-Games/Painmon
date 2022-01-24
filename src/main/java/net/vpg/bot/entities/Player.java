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

import com.mongodb.client.model.Updates;
import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import net.vpg.bot.core.Bot;
import net.vpg.bot.database.DatabaseObject;
import net.vpg.bot.pokemon.Bag;
import net.vpg.bot.pokemon.Gender;
import net.vpg.bot.pokemon.PlayerTeam;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Player extends DatabaseObject {
    public static final String COLLECTION_NAME = "players";
    public static final Pattern referencePattern = Pattern.compile("\\$\\{([A-Za-z-]+)}");
    public static final Pattern genderBasedTextSplit = Pattern.compile("\\{m:(.+);f:(.+)}");
    public static final Map<String, Player> CACHE = new HashMap<>();
    private final PlayerTeam team;
    private final Bag bag;
    // -1 if not set, 0 for female, 1 for male
    private Gender gender;
    private String position;
    private int monsCaught;

    public Player(DataObject data, Bot bot) {
        super(data, bot);
        gender = Gender.fromKey(data.getInt("gender", -1));
        bag = new Bag(data.getObject("bag"));
        position = data.getString("position", "");
        team = new PlayerTeam(data.optArray("team").orElseGet(DataArray::empty));
        monsCaught = data.getInt("monsCaught", 0);
    }

    public Player(String id, Bot bot) {
        super(id, bot);
        this.gender = Gender.GENDERLESS;
        this.team = new PlayerTeam();
        this.bag = new Bag(DataObject.empty());
        this.data.put("gender", gender.ordinal())
            .put("bag", bag)
            .put("monsCaught", monsCaught)
            .put("position", "")
            .put("party", team);
    }

    public static EntityInfo<Player> getInfo() {
        return new EntityInfo<>(COLLECTION_NAME, Player::new, CACHE);
    }

    public static Player get(String id) {
        return CACHE.get(id);
    }

    public static Player createNew(String id, Bot bot) {
        Player player = new Player(id, bot);
        player.ensureInserted();
        CACHE.put(id, player);
        return player;
    }

    public Bag getBag() {
        return bag;
    }

    public PlayablePokemon addPokemon(Pokemon base, int level) {
        PlayablePokemon pokemon = new PlayablePokemon(base, id + ":" + monsCaught, bot);
        int teamSize = team.getSize() + 1;
        if (teamSize <= 6) {
            team.setPokemon(teamSize, pokemon.setSlot(teamSize));
        }
        pokemon.setLevel(level);
        return pokemon;
    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    public Gender getGender() {
        return gender;
    }

    public Player setGender(Gender gender) {
        this.gender = gender;
        update("gender", gender.ordinal());
        return this;
    }

    public String getPosition() {
        return position;
    }

    public Player setPosition(String position) {
        this.position = position;
        update("position", position);
        return this;
    }

    public String getMention() {
        return "<@" + id + ">";
    }

    public String resolveReferences(String s) {
        s = referencePattern.matcher(s).replaceAll(m -> getProperty(m.group(1)));
        s = genderBasedTextSplit.matcher(s).replaceAll(m -> gender.isMale() ? m.group(1) : m.group(2));
        return s;
    }

    public int getMonsCaught() {
        return monsCaught;
    }

    public Player incrementMonsCaught() {
        this.monsCaught++;
        data.put("monsCaught", monsCaught);
        getCollection().updateOne(filter, Updates.inc("monsCaught", 1));
        return this;
    }

    public String getProperty(String key) {
        switch (key) {
            case "rival":
                return gender.isMale() ? "Amy" : "Toby";
            case "rival-he-she":
                return gender.isMale() ? "she" : "he";
            case "rival-him-her":
                return gender.isMale() ? "her" : "him";
            case "user-he-she":
                return gender.isMale() ? "he" : "she";
            case "user-him-her":
                return gender.isMale() ? "him" : "her";
            case "user":
                return getMention();
            case "user-id":
                return String.valueOf(id);
            default:
                return data.getString(key, "null");
        }
    }
}
