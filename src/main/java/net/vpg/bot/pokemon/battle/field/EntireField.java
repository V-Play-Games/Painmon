package net.vpg.bot.pokemon.battle.field;

public class EntireField extends Field {
    private Weather weather = Weather.NONE;
    private Terrain terrain = Terrain.NONE;
    private Zone zone = Zone.NONE;
    private Environment environment = Environment.NONE;

    public EntireField() {
        super(Type.ENTIRE_FIELD);
    }

    @Override
    public void addEffect(Field.Effect effect) {
        switch (effect.getType()) {
            case WEATHER:
                setWeather((Weather) effect);
                break;
            case TERRAIN:
                setTerrain((Terrain) effect);
                break;
            case ZONE:
                setZone((Zone) effect);
                break;
            case ENVIRONMENT:
                setEnvironment((Environment) effect);
                break;
            case ENTRY_HAZARD: // There are no entry hazards for the entire field
            case MISC:
                super.addEffect(effect);
                break;
        }
    }

    @Override
    public void removeEffect(Field.Effect effect) {
        switch (effect.getType()) {
            case WEATHER:
                if (weather == effect) {
                    setWeather(Weather.NONE);
                }
                break;
            case TERRAIN:
                if (terrain == effect) {
                    setTerrain(Terrain.NONE);
                }
                break;
            case ZONE:
                if (zone == effect) {
                    setZone(Zone.NONE);
                }
                break;
            case ENVIRONMENT:
                if (environment == effect) {
                    setEnvironment(Environment.NONE);
                }
                break;
            case ENTRY_HAZARD: // There are no entry hazards for the entire field
            case MISC:
                super.removeEffect(effect);
                break;
        }
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        removeEffect(this.weather);
        this.weather = weather;
        addEffect(this.weather);
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        removeEffect(this.terrain);
        this.terrain = terrain;
        addEffect(this.terrain);
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        removeEffect(this.zone);
        this.zone = zone;
        addEffect(this.zone);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        removeEffect(this.environment);
        this.environment = environment;
        addEffect(this.environment);
    }

    public interface Effect extends Field.Effect {
        @Override
        default boolean isEntireField() {
            return true;
        }
    }
}
