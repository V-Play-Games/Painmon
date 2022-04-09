package net.vpg.bot.pokemon;

import net.vpg.bot.entities.PokemonSpecies;

public class Capture {
    public enum Message {
        SHAKE_0("", false),
        SHAKE_1("", false),
        SHAKE_2("", false),
        SHAKE_3("", false),
        SHAKE_4("", true);

        private final String message;
        private final boolean caught;

        Message(String message, boolean caught) {
            this.message = message;
            this.caught = caught;
        }

        public String getMessage() {
            return message;
        }

        public boolean isCaught() {
            return caught;
        }
    }
}
