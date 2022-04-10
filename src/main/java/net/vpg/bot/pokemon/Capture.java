package net.vpg.bot.pokemon;

import net.vpg.bot.core.Range;
import net.vpg.bot.entities.Item;
import net.vpg.bot.entities.Pokemon;

public class Capture {
    private static final Range SHAKE_RANGE = Range.of(0, 255);

    public static Message getShakeMessage(Pokemon pokemon, int selfLevel, int level, int maxHp, int currentHp, int turn, Item ball, StatusCondition status, Method method) {
        if (ball.getId().equals("master-ball"))
            return Message.SHAKE_4;
        double hpModifier = 1 - (2.0 * currentHp) / (3 * maxHp);
        int baseRate = pokemon.getSpecies().getCaptureRate();
        double levelModifier = level > 20 ? 1 : (30 - level) / 10.0;
        double rate = baseRate * hpModifier * levelModifier;
        switch (status) {
            case SLEEP:
            case FREEZE:
                rate *= 2.5;
                break;
            case PARALYSIS:
            case POISON:
            case TOXIC_POISON:
            case BURN:
                rate *= 1.5;
                break;
        }
        switch (ball.getId()) {
            case "master-ball": // shouldn't happen but putting here just in case
            case "beast-ball": // special handling later on
            case "poke-ball":
            case "premier-ball":
            case "luxury-ball":
                break;
            case "great-ball":
                rate *= 1.5;
                break;
            case "ultra-ball":
                rate *= 2;
                break;
            case "level-ball":
                if (selfLevel > 4 * level)
                    rate *= 8;
                else if (selfLevel > 2 * level)
                    rate *= 4;
                else if (selfLevel > level)
                    rate *= 2;
                break;
            case "lure-ball":
                if (method == Method.FISH)
                    rate *= 4;
                break;
            case "heavy-ball":
                double weight = pokemon.getWeight();
                if (weight > 300)
                    rate += 30;
                else if (weight > 200)
                    rate += 20;
                else if (weight < 100)
                    rate -= 20;
                break;
            case "net-ball":
                String type = pokemon.getType().getName();
                if (type.contains("bug") || type.contains("water"))
                    rate *= 3.5;
                break;
            case "nest-ball":
                if (level < 30)
                    rate *= (41 - level) / 10.0;
                break;
            case "repeat-ball":
                // TODO: Figure out how to implement this (rate*3.5 if species is already caught)
                break;
            case "timer-ball":
                rate *= Math.max(4, (1.0 + turn) * 1229 / 4096);
                break;
            case "dive-ball":
                if (method == Method.SURF || method == Method.FISH || method == Method.DIVE)
                    rate *= 3.5;
                break;
            case "dusk-ball":
                if (method == Method.CAVE)
                    rate *= 3.5;
                break;
            case "quick-ball":
                if (turn == 1)
                    rate *= 5;
                break;
            default:
                throw new IllegalStateException(ball + " is not supported as a poke ball.");
        }
        boolean ub = pokemon.getAbilities().stream().map(Pokemon.AbilitySlot::getId).anyMatch(id -> id.equals("beast-boost"));
        boolean beastBall = ball.getId().equals("beast-ball");
        if (ub) {
            if (beastBall) {
                rate *= 5;
            } else {
                rate *= 0.1;
            }
        } else if (beastBall) {
            rate *= 0.1;
        }
        rate = Math.max((int) rate, 1);
        // TODO: Figure out about critical captures
        int shakeRange = (int) (65536 / Math.pow(255 / rate, 0.1875));
        int i = 0;
        while (i < 4 && SHAKE_RANGE.random() < shakeRange) i++;
        return Message.values()[i];
    }

    public enum Message {
        SHAKE_0("Oh yikes! The Pokemon broke free!", false),
        SHAKE_1("Darn it! It appeared to be caught!", false),
        SHAKE_2("Argh! So close!", false),
        SHAKE_3("Gah! Come on! You've gotta be kidding me!", false),
        SHAKE_4("Woohoo! Gotcha! %s was caught!", true);

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

    public enum Method {
        LAND,
        CAVE,
        SURF,
        DIVE,
        FISH;
    }
}
