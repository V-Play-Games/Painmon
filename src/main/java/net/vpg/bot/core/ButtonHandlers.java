package net.vpg.bot.core;

import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.vpg.bot.framework.ButtonHandler;
import net.vpg.bot.entities.Dialogue;

public interface ButtonHandlers {
    class Area implements ButtonHandler {
        @Override
        public String getName() {
            return "area";
        }

        @Override
        public void handle(ButtonClickEvent e, String[] args) {
            if (!args[3].equals(e.getUser().getId())) return;
            Dialogue.get(args[0]).executeActions(e, args[1], args[2]);
        }
    }
}
