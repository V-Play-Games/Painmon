package net.vpg.bot.pokemon.battle;

import net.vpg.bot.core.Range;
import net.vpg.bot.pokemon.Stat;
import net.vpg.bot.pokemon.StatMapping;

import java.util.Map;

import static net.vpg.bot.pokemon.Stat.*;

public class BattleStatMapping extends StatMapping {
    private static final Range STAT_CHANGE_RANGE = Range.of(-6, 6);
    private static final Range CRIT_RATE_RANGE = Range.of(0, 3);
    protected int evasion;
    protected int accuracy;
    protected int critRate;

    public BattleStatMapping() {
    }

    public BattleStatMapping(StatMapping stats) {
        super(stats);
    }

    public BattleStatMapping(BattleStatMapping stats) {
        this((StatMapping) stats);
        this.evasion = stats.evasion;
        this.accuracy = stats.accuracy;
        this.critRate = stats.critRate;
    }

    private static void checkStatRange(Stat stat, int value) {
        if (!(stat == CRIT_RATE ? CRIT_RATE_RANGE : STAT_CHANGE_RANGE).contains(value)) {
            throw new IllegalArgumentException(stat + " cannot have " + value + " stat changes");
        }
    }

    @Override
    public int getHP() {
        return 0;
    }

    @Override
    public void setHP(int hp) {
    }

    @Override
    public void setAttack(int attack) {
        checkStatRange(ATTACK, hp);
        super.setAttack(attack);
    }

    @Override
    public void setDefense(int defense) {
        checkStatRange(DEFENSE, hp);
        super.setDefense(defense);
    }

    @Override
    public void setSpAtk(int spAtk) {
        checkStatRange(SP_ATK, hp);
        super.setSpAtk(spAtk);
    }

    @Override
    public void setSpDef(int spDef) {
        checkStatRange(SP_DEF, hp);
        super.setSpDef(spDef);
    }

    @Override
    public void setSpeed(int speed) {
        checkStatRange(SPEED, hp);
        super.setSpeed(speed);
    }

    public int getEvasion() {
        return evasion;
    }

    public void setEvasion(int evasion) {
        checkStatRange(EVASION, hp);
        this.evasion = evasion;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        checkStatRange(ACCURACY, hp);
        this.accuracy = accuracy;
    }

    public int getCritRate() {
        return critRate;
    }

    public void setCritRate(int critRate) {
        checkStatRange(CRIT_RATE, hp);
        this.critRate = critRate;
    }

    public int getStat(Stat stat) {
        switch (stat) {
            case EVASION:
                return evasion;
            case ACCURACY:
                return accuracy;
            case CRIT_RATE:
                return critRate;
            default:
                return super.getStat(stat);
        }
    }

    @Override
    public void setStat(Stat stat, int value) {
        switch (stat) {
            case EVASION:
                setEvasion(value);
                break;
            case ACCURACY:
                setAccuracy(value);
                break;
            case CRIT_RATE:
                setCritRate(value);
                break;
            default:
                super.setStat(stat, value);
        }
    }

    @Override
    public Map<Stat, Integer> toMap() {
        Map<Stat, Integer> statMap = super.toMap();
        statMap.put(EVASION, evasion);
        statMap.put(ACCURACY, accuracy);
        statMap.put(CRIT_RATE, critRate);
        return statMap;
    }
}
