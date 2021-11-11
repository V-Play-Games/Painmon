package net.vpg.bot.commands.manager;

import net.vpg.bot.framework.Bot;
import net.vpg.bot.framework.commands.BotCommandImpl;
import net.vpg.bot.framework.commands.CommandReceivedEvent;
import net.vpg.bot.framework.commands.ManagerCommand;

public class AddPkmnCommand extends BotCommandImpl implements ManagerCommand {
    public AddPkmnCommand(Bot bot) {
        super(bot, "addpkmn", "a");
    }

    @Override
    public void onCommandRun(CommandReceivedEvent e) throws Exception {

    }

    @Override
    public void onSlashCommandRun(CommandReceivedEvent e) throws Exception {

    }
}
