package net.vpg.bot.pokemon.battle.field;

public enum Weather implements EntireField.Effect {
    NONE, SUN, RAIN, SAND, HAIL, FOG;

    public boolean isFog() {
        return this == FOG;
    }

    @Override
    public Type getType() {
        return Type.WEATHER;
    }
}
