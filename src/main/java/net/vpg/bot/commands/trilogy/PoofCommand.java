package net.vpg.bot.commands.trilogy;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.vpg.bot.commands.BotCommandImpl;
import net.vpg.bot.core.Bot;
import net.vpg.bot.entities.Player;
import net.vpg.bot.event.CommandReceivedEvent;
import net.vpg.bot.event.SlashCommandReceivedEvent;
import net.vpg.bot.event.TextCommandReceivedEvent;

public class PoofCommand extends BotCommandImpl {
    public PoofCommand(Bot bot) {
        super(bot, "poof", "poof data");
        setMinArgs(1);
        setMaxArgs(1);
        addOption(OptionType.STRING, "id", "id of the player to poof", true);
    }

    @Override
    public void onTextCommandRun(TextCommandReceivedEvent e) {
        execute(e, e.getArg(0));
    }

    @Override
    public void onSlashCommandRun(SlashCommandReceivedEvent e) {
        execute(e, e.getString("id"));
    }

    public void execute(CommandReceivedEvent e, String id) {
        Player player = Player.get(id);
        if (player == null) {
            e.send("Player does not exist").queue();
        } else {
            player.delete();
            Player.CACHE.remove(id);
            e.send("Player poof").queue();
        }
    }
}