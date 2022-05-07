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
