package net.vpg.bot.commands.trilogy;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.vpg.bot.core.Bot;
import net.vpg.bot.entities.Player;
import net.vpg.bot.event.CommandReceivedEvent;
import net.vpg.bot.event.SlashCommandReceivedEvent;
import net.vpg.bot.event.TextCommandReceivedEvent;
import net.vpg.bot.pokemon.PlayerPokemon;
import net.vpg.bot.pokemon.StatMapping;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ViewCommand extends TrilogyCommand {
    public ViewCommand(Bot bot) {
        super(bot, "view", "View details about a Pokemon owned");
        setMinArgs(1);
        setMaxArgs(1);
        addOption(OptionType.INTEGER, "id", "Id of the pokemon to view.", true);
    }

    @Override
    public void onTextCommandRun(TextCommandReceivedEvent e) {
        execute(e, Integer.parseInt(e.getArg(0)));
    }

    @Override
    public void onSlashCommandRun(SlashCommandReceivedEvent e) {
        execute(e, (int) e.getLong("id"));
    }

    public void execute(CommandReceivedEvent e, int id) {
        Player player = Player.get(e.getUser().getId());
        PlayerPokemon pokemon = player.getPokemonOwned()
            .stream()
            .map(PlayerPokemon::get)
            .filter(p -> p.getPlayerSpecificId() == id)
            .findFirst()
            .orElse(null);
        if (pokemon == null) {
            e.send("Cannot find any pokemon with the id " + id).setEphemeral(true).queue();
            return;
        }
        StatMapping stats = pokemon.getStats();
        StatMapping ivs = pokemon.getIvs();
        StatMapping evs = pokemon.getEvs();
        e.sendEmbeds(new EmbedBuilder()
            .setTitle((pokemon.getNickname() == null ? "" : pokemon.getNickname() + " the ") + pokemon.getBase().getName())
            .addField("General Info",
                "**Shiny**: " + pokemon.isShiny() +
                    "\n**Gender**: " + pokemon.getGender() +
                    "\n**Level**: " + pokemon.getLevel() +
                    "\n**Ability**: " + pokemon.getAbility().getName() +
                    "\n**Nature**: " + pokemon.getNature() +
                    "\n**Held Item**: " + pokemon.getHeldItem().getId(),
                false)
            .addField("Moves",
                Arrays.stream(pokemon.getMoveset().getMoves())
                    .map(move -> String.format("**%s** (%d/%d)", move.getMove().getName(), move.getCurrentPP(), move.getMaxPP()))
                    .collect(Collectors.joining("\n")),
                false)
            .addField("Stats",
                String.format("```\n" +
                        "Stat    | Val | IV | EV\n" +
                        "HP      | %3d | %2d | %d\n" +
                        "Attack  | %3d | %2d | %d\n" +
                        "Defense | %3d | %2d | %d\n" +
                        "Sp. Atk | %3d | %2d | %d\n" +
                        "Sp. Def | %3d | %2d | %d\n" +
                        "Speed   | %3d | %2d | %d\n" +
                        "```",
                    stats.getHP(), ivs.getHP(), evs.getHP(),
                    stats.getAttack(), ivs.getAttack(), evs.getAttack(),
                    stats.getDefense(), ivs.getDefense(), evs.getDefense(),
                    stats.getSpAtk(), ivs.getSpAtk(), evs.getSpAtk(),
                    stats.getSpDef(), ivs.getSpDef(), evs.getSpDef(),
                    stats.getSpeed(), ivs.getSpeed(), evs.getSpeed()
                ),
                false)
            .addField("Battle Status",
                "**HP**: " + pokemon.getCurrentHP() + "/" + pokemon.getMaxHP() +
                    "\n**Status**: " + pokemon.getStatus(),
                false)
            .build()).queue();
    }
}