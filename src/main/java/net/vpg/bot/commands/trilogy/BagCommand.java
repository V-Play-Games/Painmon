/*
 * Copyright 2021 Vaibhav Nargwani
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.vpg.bot.commands.trilogy;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.vpg.bot.action.Sender;
import net.vpg.bot.commands.BotCommandImpl;
import net.vpg.bot.core.Bot;
import net.vpg.bot.entities.Player;
import net.vpg.bot.event.SlashCommandReceivedEvent;
import net.vpg.bot.event.TextCommandReceivedEvent;

import java.util.Map;
import java.util.stream.Collectors;

public class BagCommand extends BotCommandImpl implements TrilogyCommand {
    public BagCommand(Bot bot) {
        super(bot, "bag", "View your bag");
        addOption(OptionType.INTEGER, "page", "Which page of your bag you want to see");
        setMaxArgs(1);
    }

    public static void execute(Sender e, Player player, long page) {
        Map<String, Integer> items = player.getBag().getItems();
        long maxPages = (long) Math.ceil(items.size() / 10.0);
        long actualPage = Math.min(maxPages, Math.max(1, page));
        String list = items.entrySet()
            .stream()
            .skip(10 * (actualPage - 1))
            .limit(10)
            .map(entry -> entry.getKey() + " x" + entry.getValue())
            .collect(Collectors.joining("\n"));
        e.sendEmbeds(
            new EmbedBuilder()
                .setTitle(player.getMention() + "'s bag")
                .setDescription(list)
                .setFooter("Page " + actualPage + "/" + maxPages)
                .build())
            .setActionRow(
                Button.primary("bag:" + player.getId() + ":" + ++actualPage, Emoji.fromUnicode("")),
                Button.primary("bag:" + player.getId() + ":" + --actualPage, Emoji.fromUnicode("")),
                Button.primary("bag:" + player.getId() + ":" + --actualPage, Emoji.fromUnicode(""))
            ).queue();
    }

    @Override
    public void onTextCommandRun(TextCommandReceivedEvent e, Player player) {
        execute(e, player, e.getArgs().size() != 0 ? Long.parseLong(e.getArg(0)) : 0);
    }

    @Override
    public void onSlashCommandRun(SlashCommandReceivedEvent e, Player player) {
        execute(e, player, e.getLong("page"));
    }
}
