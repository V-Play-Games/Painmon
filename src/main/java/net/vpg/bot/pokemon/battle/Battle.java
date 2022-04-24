package net.vpg.bot.pokemon.battle;

import net.vpg.bot.entities.Player;
import net.vpg.bot.pokemon.WildPokemon;
import net.vpg.bot.pokemon.battle.field.EntireField;
import net.vpg.bot.pokemon.battle.field.Field;

import java.util.HashMap;
import java.util.Map;

public class Battle {
    private final Map<String, BattlePokemon> pokemon = new HashMap<>();
    private final Map<String, Field.Type> fieldOwners = new HashMap<>();
    private final Map<Field.Type, Field> fields = createFields();
    private final EntireField entireField = new EntireField();

    public static Battle between(Player player, WildPokemon spawn) {
        return new Battle(); // TODO: figure out the rest of initialization stuff
    }

    protected Map<Field.Type, Field> createFields() {
        return Map.of(
            Field.Type.PLAYER, new Field(Field.Type.PLAYER),
            Field.Type.NPC, new Field(Field.Type.NPC)
        );
    }

    // TODO: Revisit this when PvP will be designed
    public enum Type {
        // will be implemented
        WILD_SINGLES(true),
        SINGLES(false),
        // unimplemented
        WILD_SINGLE_DOUBLES(true),
        WILD_AI_DOUBLES(true),
        SINGLE_DOUBLES(false),
        AI_DOUBLES(false);

        private final boolean catchAllowed;

        Type(boolean catchAllowed) {
            this.catchAllowed = catchAllowed;
        }

        public boolean isCatchAllowed() {
            return catchAllowed;
        }
    }
}
