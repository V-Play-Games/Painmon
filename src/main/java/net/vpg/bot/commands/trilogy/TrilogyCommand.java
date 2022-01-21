package net.vpg.bot.commands.trilogy;

import net.vpg.bot.commands.BotCommandImpl;
import net.vpg.bot.commands.event.CommandReceivedEvent;
import net.vpg.bot.entities.Player;
import net.vpg.bot.framework.Bot;

public abstract class TrilogyCommand extends BotCommandImpl {
    public TrilogyCommand(Bot bot, String name, String description, String... aliases) {
        super(bot, name, description, aliases);
    }

    @Override
    public boolean runChecks(CommandReceivedEvent e) {
        if (!super.runChecks(e)) return false;
        Player player = Player.get(e.getUser().getId());
        if (player == null) {
            e.send("Please start your journey with `" + e.getPrefix() + "start` command first").setEphemeral(true).queue();
            return true;
        }
        return false;
    }
}
