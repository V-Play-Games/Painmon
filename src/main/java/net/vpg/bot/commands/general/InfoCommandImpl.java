package net.vpg.bot.commands.general;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.vpg.bot.framework.Bot;
import net.vpg.bot.framework.commands.CommandReceivedEvent;

import java.time.Instant;

public class InfoCommandImpl extends InfoCommand {
    public InfoCommandImpl(Bot bot) {
        super(bot, "info about the bot");
    }

    @Override
    protected MessageEmbed getEmbed(CommandReceivedEvent e) {
        return new EmbedBuilder()
            .setAuthor("V Play Games Bot Info")
            .setDescription("A Pokemon-related discord bot created, developed & maintained by")
            .appendDescription(" V Play Games aka VPG (<@" + bot.getOwnerId() + ">)")
            .addField("Developer", "<@" + bot.getOwnerId() + ">", true)
            .addField("Version", "", true)
            .addField("Server Count", String.valueOf(e.getJDA().getGuilds().size()), false)
            .setTimestamp(Instant.now())
            .setThumbnail(e.getJDA().getSelfUser().getAvatarUrl())
            .setColor(0x1abc9c)
            .build();
    }
}
