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

import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.vpg.bot.action.Sender;
import net.vpg.bot.commands.BotCommandImpl;
import net.vpg.bot.core.Bot;
import net.vpg.bot.entities.Player;
import net.vpg.bot.event.CommandReceivedEvent;
import net.vpg.bot.pokemon.PlayerPokemon;

import java.util.Arrays;

public class TeamCommand extends BotCommandImpl implements NoArgsTrilogyCommand {
    public TeamCommand(Bot bot) {
        super(bot, "team", "View your team");
    }

    public static void sendTeam(Sender e, Player player) {
        Button[] buttons = Arrays.stream(player.getTeam().getPokemon())
            .map(TeamCommand::makeButton)
            .toArray(Button[]::new);
        e.send(player.getMention() + "'s team").setActionRows(
            ActionRow.of(buttons[0], buttons[1]),
            ActionRow.of(buttons[2], buttons[3]),
            ActionRow.of(buttons[4], buttons[5])
        ).queue();
    }

    private static Button makeButton(PlayerPokemon pokemon) {
        return pokemon == null
            ? Button.primary(Math.random() + "", "Empty").asDisabled()
            : pokemon.getCurrentHP() == 0
            ? Button.danger("view:" + pokemon.getId(), pokemon.getEffectiveName())
            : Button.primary("view:" + pokemon.getId(), pokemon.getEffectiveName());
    }

    @Override
    public void execute(CommandReceivedEvent e, Player player) {
        sendTeam(e, player);
    }
}
