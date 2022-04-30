package net.vpg.bot.pokemon.battle;

import net.vpg.bot.core.MiscUtil;
import net.vpg.bot.pokemon.PokemonData;
import net.vpg.bot.pokemon.PokemonTeam;
import net.vpg.bot.pokemon.TrainerPokemon;
import net.vpg.bot.pokemon.WildPokemon;
import net.vpg.bot.pokemon.battle.field.EntireField;
import net.vpg.bot.pokemon.battle.field.Field;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static net.vpg.bot.pokemon.battle.field.Field.Type.NPC;
import static net.vpg.bot.pokemon.battle.field.Field.Type.PLAYER;

public class Battle {
    private final Map<String, BattlePokemon> pokemon;
    private final Map<Position, String> positions;
    private final Map<String, Field.Type> fieldOwners;
    private final Map<Field.Type, Field> fields;
    private final EntireField entireField = new EntireField();
    private int turn;

    public Battle(Map<String, BattlePokemon> pokemon,
                  Map<Position, String> positions,
                  Map<String, Field.Type> fieldOwners,
                  Map<Field.Type, Field> fields) {
        this.pokemon = pokemon;
        this.positions = positions;
        this.fieldOwners = fieldOwners;
        this.fields = fields;
    }

    public static Battle between(PokemonTeam team, WildPokemon spawn) {
        TrainerPokemon lead = team.getLead();
        return new Battle(
            getPokemonMap(team.getPokemon(), spawn),
            new MiscUtil.MapBuilder<Position, String>()
                .put(Position.POSITION_1, lead.getId())
                .put(Position.POSITION_2, spawn.getId())
                .toMap(),
            Map.of(
                lead.getTrainerId(), PLAYER,
                "SPAWN", NPC
            ),
            Field.makeFieldMap(PLAYER, NPC));
    }

    public static Battle between(PokemonTeam playerTeam, PokemonTeam opponentTeam) {
        TrainerPokemon playerLead = playerTeam.getLead();
        TrainerPokemon opponentLead = opponentTeam.getLead();
        return new Battle(
            getPokemonMap(playerTeam.getPokemon(), opponentTeam.getPokemon()),
            new MiscUtil.MapBuilder<Position, String>()
                .put(Position.POSITION_1, playerLead.getId())
                .put(Position.POSITION_2, opponentLead.getId())
                .toMap(),
            Map.of(
                playerLead.getTrainerId(), PLAYER,
                opponentLead.getTrainerId(), NPC
            ),
            Field.makeFieldMap(PLAYER, NPC));
    }

    private static Map<String, BattlePokemon> getPokemonMap(PokemonData[] list1, PokemonData... list2) {
        return getPokemonMap(Stream.concat(Stream.of(list1), Stream.of(list2)));
    }

    private static Map<String, BattlePokemon> getPokemonMap(PokemonData[] list1, PokemonData[] list2, PokemonData[] list3) {
        return getPokemonMap(Stream.concat(Stream.concat(Stream.of(list1), Stream.of(list2)), Stream.of(list3)));
    }

    private static Map<String, BattlePokemon> getPokemonMap(Stream<PokemonData> stream) {
        return stream.collect(Collectors.toMap(PokemonData::getId, PokemonData::prepareForBattle));
    }

    public Map<String, BattlePokemon> getPokemon() {
        return pokemon;
    }

    public Map<Position, String> getPositions() {
        return positions;
    }

    public Map<String, Field.Type> getFieldOwners() {
        return fieldOwners;
    }

    public Map<Field.Type, Field> getFields() {
        return fields;
    }

    public EntireField getEntireField() {
        return entireField;
    }

    public int getTurn() {
        return turn;
    }

    public void incrementTurn() {
        turn++;
    }

    public enum Type {
        // will be implemented
        WILD_SINGLES(true),
        TRAINER_SINGLES(false),
        // unimplemented
        WILD_SINGLE_DOUBLES(true),
        WILD_AI_DOUBLES(true),
        TRAINER_SOLO_DOUBLES(false),
        TRAINER_AI_DOUBLES(false);

        private final boolean catchAllowed;

        Type(boolean catchAllowed) {
            this.catchAllowed = catchAllowed;
        }

        public boolean isCatchAllowed() {
            return catchAllowed;
        }
    }

    public enum Position {
        POSITION_1, POSITION_2, POSITION_3, POSITION_4
    }
}
