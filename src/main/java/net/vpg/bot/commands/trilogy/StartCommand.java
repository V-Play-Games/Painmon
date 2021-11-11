package net.vpg.bot.commands.trilogy;

import net.vpg.bot.core.Player;
import net.vpg.bot.entities.Dialogue;
import net.vpg.bot.framework.Bot;
import net.vpg.bot.framework.commands.BotCommandImpl;
import net.vpg.bot.framework.commands.CommandReceivedEvent;
import net.vpg.bot.framework.commands.NoArgsCommand;

public class StartCommand extends BotCommandImpl implements NoArgsCommand {
    public StartCommand(Bot bot) {
        super(bot, "start", "Starts a new story.");
    }

    @Override
    public boolean runChecks(CommandReceivedEvent e) {
        if (Player.get(e.getUser().getId()) != null) {
            e.send("You have already started your journey, Go on and play the game using `v!game` command").queue();
            return false;
        }
        return true;
    }

    public void execute(CommandReceivedEvent e) {
        Player player = Player.createNew(e.getUser().getId(), bot);
        player.setPosition("paseagon.welcome");
        Dialogue.get("paseagon.welcome").send(e, player);
    }
}
