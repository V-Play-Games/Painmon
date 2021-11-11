package net.vpg.bot.commands.trilogy;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.vpg.bot.entities.Move;
import net.vpg.bot.entities.Pokemon;
import net.vpg.bot.framework.Bot;
import net.vpg.bot.framework.commands.BotCommandImpl;
import net.vpg.bot.framework.commands.CommandReceivedEvent;

import java.util.Comparator;
import java.util.stream.Collectors;

public class SearchCommand extends BotCommandImpl {
    public SearchCommand(Bot bot) {
        super(bot, "search", "Search for a Pokemon, Move or Ability");
        addOption(OptionType.STRING, "term", "The term to search", true);
    }

    @Override
    public void onCommandRun(CommandReceivedEvent e) {
        execute(e, String.join(" ", e.getArgsFrom(1)));
    }

    @Override
    public void onSlashCommandRun(CommandReceivedEvent e) {
        execute(e, e.getString("term"));
    }

    public void execute(CommandReceivedEvent e, String searchTerm) {
        String toSearch = searchTerm.replaceAll("[\\s\n-/\\\\_:]+", "-").toLowerCase();
        Pokemon p = Pokemon.get(toSearch);
        if (p != null) {
            e.sendEmbeds(
                new EmbedBuilder()
                    .setTitle(p.getName())
                    .setDescription("General Info:" +
                        "\n**Type**: " + p.getType().getName() +
                        "\n**Base Stats**: " + p.getBaseStats() +
                        "\n**EV Yield**: " + p.getEvYield() +
                        "\n**EXP Yield**: " + p.getExpYield())
                    .addField("Possible Abilities", p.getAbilities()
                        .stream()
                        .map(slot -> slot.getAbility().getName() + (slot.isHidden() ? " (Hidden)" : ""))
                        .collect(Collectors.joining(", ")), false).build(),
                new EmbedBuilder()
                    .setDescription("**Moveset**\n" +
                        p.getMoveset()
                            .entrySet()
                            .stream()
                            .sorted(Comparator.comparing(entry -> entry.getValue().get(0).getOrderingKey() + entry.getKey()))
                            .map(entry -> Move.get(entry.getKey()).getName() + " " + entry.getValue()
                                .stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(", and ")))
                            .collect(Collectors.joining("\n"))).build()
            ).queue();
            return;
        }
        Move m = Move.get(toSearch);
        if (m != null) {
            e.sendEmbeds(
                new EmbedBuilder()
                    .setTitle(m.getName())
                    .setDescription("__**General Info:**__" +
                        "\n**Description**: " + m.getDescription() +
                        "\n**Type**: " + m.getType().getName() +
                        "\n**Effect**: " + m.getEffect() +
                        "\n**Accuracy**: " + m.getAccuracy() +
                        "\n**Power**: " + m.getPower() +
                        "\n**Category**: " + m.getCategory().getName() +
                        "\n**Base PP**: " + m.getPP() +
                        "\n**Priority**: " + (m.getPriority() > 0 ? "+" : "") + m.getPriority() +
                        "\n**Target**: " + m.getTarget().getName() +
                        "\n\n__**Metadata:**__\n" + m.getMetadata().toString()).build()
            ).queue();
        }
    }
}
