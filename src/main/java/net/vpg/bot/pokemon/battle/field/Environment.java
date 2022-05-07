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
package net.vpg.bot.pokemon.battle.field;

public enum Environment implements EntireField.Effect {
    // @formatter:off
    NONE          (Weather.NONE, Terrain.NONE),
    // Weather
    SUN           (Weather.SUN,  Terrain.NONE),
    RAIN          (Weather.RAIN, Terrain.NONE),
    SANDSTORM     (Weather.SAND, Terrain.NONE),
    HAIL          (Weather.HAIL, Terrain.NONE),
//    FOG           (Weather.FOG, Terrain.NONE),
    // Terrain
    CHILLY_FIELD  (Weather.NONE, Terrain.ICY),
    DUSTY_FIELD   (Weather.NONE, Terrain.DUSTY),
    MIST          (Weather.NONE, Terrain.MISTY),
    WINDY_FIELD   (Weather.NONE, Terrain.BREEZY),
    ELECTRIC_FIELD(Weather.NONE, Terrain.ELECTRIC),
    MAGNETIC_FIELD(Weather.NONE, Terrain.MAGNETIC),
    POLLUTED_AREA (Weather.NONE, Terrain.ACIDIC),
    // Hybrid
    THUNDERSTORM  (Weather.RAIN, Terrain.ELECTRIC),
    STORM_SURGE   (Weather.RAIN, Terrain.BREEZY),
    DUSTY_WINDS   (Weather.SAND, Terrain.BREEZY),
    CHILLY_WINDS  (Weather.HAIL, Terrain.BREEZY);
    // @formatter:on

    private final Weather weather;
    private final Terrain terrain;

    Environment(Weather weather, Terrain terrain) {
        this.weather = weather;
        this.terrain = terrain;
    }

    @Override
    public Type getType() {
        return Type.ENVIRONMENT;
    }

    public Weather getWeather() {
        return weather;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
