package net.vpg.bot.pokemon.battle.field;

public enum Zone implements EntireField.Effect {
    NONE, BUG, DRAGON, FIGHTING, NORMAL;

    @Override
    public Type getType() {
        return Type.ZONE;
    }
}
