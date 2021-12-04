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

import net.vpg.bot.entities.Player;
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
