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

import net.vpg.bot.commands.NoArgsCommand;
import net.vpg.bot.core.Bot;
import net.vpg.bot.entities.Area;
import net.vpg.bot.entities.Player;
import net.vpg.bot.event.CommandReceivedEvent;

public class PlayCommand extends TrilogyCommand implements NoArgsCommand {
    public PlayCommand(Bot bot) {
        super(bot, "play", "Continue playing from where you left off!");
    }

    public void execute(CommandReceivedEvent e) {
        Player player = Player.get(e.getUser().getId());
        Area.get(player.getPosition()).send(e, e.getUser());
    }
}
