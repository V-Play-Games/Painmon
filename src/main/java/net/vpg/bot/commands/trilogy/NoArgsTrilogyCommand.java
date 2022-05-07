package net.vpg.bot.commands.trilogy;

import net.vpg.bot.commands.NoArgsCommand;
import net.vpg.bot.entities.Player;
import net.vpg.bot.event.CommandReceivedEvent;
import net.vpg.bot.event.SlashCommandReceivedEvent;
import net.vpg.bot.event.TextCommandReceivedEvent;

public interface NoArgsTrilogyCommand extends TrilogyCommand, NoArgsCommand {
    @Override
    default void execute(CommandReceivedEvent e) throws Exception {
        Player player = checkPlayer(e);
        if (player != null) {
            execute(e, player);
        }
    }

    @Override
    default void onTextCommandRun(TextCommandReceivedEvent e) throws Exception {
        execute(e);
    }

    @Override
    default void onSlashCommandRun(SlashCommandReceivedEvent e) throws Exception {
        execute(e);
    }

    @Override
    default void onTextCommandRun(TextCommandReceivedEvent e, Player player) throws Exception {
        execute(e, player);
    }

    @Override
    default void onSlashCommandRun(SlashCommandReceivedEvent e, Player player) throws Exception {
        execute(e, player);
    }

    void execute(CommandReceivedEvent e, Player player) throws Exception;
}
