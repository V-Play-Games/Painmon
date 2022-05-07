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

import net.vpg.bot.commands.BotCommand;
import net.vpg.bot.entities.Player;
import net.vpg.bot.event.CommandReceivedEvent;
import net.vpg.bot.event.SlashCommandReceivedEvent;
import net.vpg.bot.event.TextCommandReceivedEvent;

public interface TrilogyCommand extends BotCommand {
    @Override
    default void onTextCommandRun(TextCommandReceivedEvent e) throws Exception {
        Player player = checkPlayer(e);
        if (player != null) {
            onTextCommandRun(e, player);
        }
    }

    @Override
    default void onSlashCommandRun(SlashCommandReceivedEvent e) throws Exception {
        Player player = checkPlayer(e);
        if (player != null) {
            onSlashCommandRun(e, player);
        }
    }

    void onTextCommandRun(TextCommandReceivedEvent e, Player player) throws Exception;

    void onSlashCommandRun(SlashCommandReceivedEvent e, Player player) throws Exception;

    default Player checkPlayer(CommandReceivedEvent e) {
        Player player = Player.get(e.getUser().getId());
        if (player == null) {
            e.send("Please start your journey with `" + e.getPrefix() + "start` command first").setEphemeral(true).queue();
        }
        return player;
    }
}
