package net.vpg.bot.core;

import net.vpg.bot.entities.Dialogue;
import net.vpg.bot.framework.BotButtonEvent;
import net.vpg.bot.framework.ButtonHandler;

public interface ButtonHandlers {
    class Area implements ButtonHandler {
        @Override
        public String getName() {
            return "area";
        }

        @Override
        public void handle(BotButtonEvent e) {
            if (!e.getArg(3).equals(e.getUser().getId())) return;
            Dialogue.get(e.getArg(0)).executeActions(e, e.getArg(1), e.getArg(2));
        }
    }
}
