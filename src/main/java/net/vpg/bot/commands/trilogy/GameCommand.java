package net.vpg.bot.commands.trilogy;

import net.vpg.bot.core.Player;
import net.vpg.bot.entities.Dialogue;
import net.vpg.bot.framework.Bot;
import net.vpg.bot.framework.commands.BotCommandImpl;
import net.vpg.bot.framework.commands.CommandReceivedEvent;
import net.vpg.bot.framework.commands.NoArgsCommand;

public class GameCommand extends BotCommandImpl implements NoArgsCommand {
    public GameCommand(Bot bot) {
        super(bot, "game", "Continue playing from where you left off!");
    }

    public void execute(CommandReceivedEvent e) {
        Player player = Player.get(e.getUser().getId());
        if (player == null) {
            e.send("Please start your journey with `v!start` command first").queue();
            return;
        }
        Dialogue.get(player.getPosition()).send(e);
    }
}
