import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DataTrimmer {
    public static void main(String[] args) throws FileNotFoundException {
        try (PrintStream output = new PrintStream("trimmed.json")) {
            output.println("[");
            trimPokes(output);
            output.println("{}]");
        }
    }

    public static void trimMoves(PrintStream output) throws FileNotFoundException {
        DataArray.fromJson(new FileReader("move.json"))
            .stream(DataArray::getObject)
            .filter(data -> data.keys().size() != 0)
            .map(data -> DataObject.empty()
                .put("id", data.get("id"))
                .put("name", data.get("name"))
                .put("pp", data.opt("pp").orElse(null))
                .put("accuracy", data.opt("accuracy").orElse(null))
                .put("priority", data.get("priority"))
                .put("type", data.getObject("type").get("name"))
                .put("target", data.getObject("target").get("name"))
                .put("category", data.optObject("damage_class").map(cat -> cat.get("name")).orElse(null))
                .put("meta", data.optObject("meta")
                    .map(meta -> meta.put("category", meta.getObject("category").getString("name"))
                        .put("ailment", meta.getObject("ailment").getString("name")))
                    .orElse(null))
                .put("effect_chance", data.opt("effect_chance").orElse(null))
                .put("power", data.opt("power").orElse(null))
                .put("effect", data.getArray("effect_entries")
                    .stream(DataArray::getObject)
                    .filter(entry -> entry.getObject("language").getString("name").equals("en"))
                    .findAny()
                    .map(entry -> entry.getString("short_effect"))
                    .orElse(null))
                .put("description", data.getArray("flavor_text_entries")
                    .stream(DataArray::getObject)
                    .filter(entry -> entry.getObject("version_group").getString("name").equals("ultra-sun-ultra-moon"))
                    .filter(entry -> entry.getObject("language").getString("name").equals("en"))
                    .findFirst()
                    .map(entry -> entry.getString("flavor_text").replaceAll("\n", " "))
                    .orElse(null))
            )
            .filter(data -> data.opt("description").isPresent())
            .forEach(data -> output.print(data.toPrettyString() + ", "));
    }

    public static void trimAbilities(PrintStream output) throws FileNotFoundException {
        DataArray.fromJson(new FileReader("ability.json"))
            .stream(DataArray::getObject)
            .filter(data -> data.keys().size() != 0)
            .filter(data -> data.getBoolean("is_main_series"))
            .map(data -> DataObject.empty()
                .put("name", data.get("name"))
                .put("id", data.get("id"))
                .put("effect", data.getArray("effect_entries")
                    .stream(DataArray::getObject)
                    .peek(System.out::println)
                    .filter(entry -> entry.getObject("language").getString("url").equals("https://pokeapi.co/api/v2/language/9/"))
                    .findAny()
                    .map(entry -> entry.getString("short_effect"))
                    .orElse(null))
                .put("description", data.getArray("flavor_text_entries")
                    .stream(DataArray::getObject)
                    .filter(entry -> entry.getObject("version_group").getString("name").equals("ultra-sun-ultra-moon"))
                    .filter(entry -> entry.getObject("language").getString("name").equals("en"))
                    .findFirst()
                    .map(entry -> entry.getString("flavor_text").replaceAll("\n", " "))
                    .orElse(null))
            )
            .filter(data -> data.opt("description").isPresent())
            .forEach(data -> output.print(data.toPrettyString() + ", "));
    }

    public static void trimPokes(PrintStream output) throws FileNotFoundException {
        DataArray.fromJson(new FileReader("pokemon.json"))
            .stream(DataArray::getObject)
            .filter(data -> data.keys().size() != 0)
            .map(data -> DataObject.empty()
                .put("name", data.getString("name"))
                .put("id", data.getInt("id"))
                .put("type",
                    data.getArray("types")
                        .stream(DataArray::getObject)
                        .sorted(Comparator.comparingInt(type -> type.getInt("slot")))
                        .map(type -> type.getObject("type").getString("name"))
                        .collect(Collectors.joining("-")))
                .put("exp", data.getInt("base_experience"))
                .put("default", data.getBoolean("is_default"))
                .put("abilities",
                    data.getArray("abilities")
                        .stream(DataArray::getObject)
                        .map(ability -> ability.put("ability", ability.getObject("ability").getString("name")))
                        .map(DataObject::toMap)
                        .collect(Collectors.toList()))
                .put("species", data.getObject("species").getString("name"))
                .put("stats",
                    data.getArray("stats")
                        .stream(DataArray::getObject)
                        .collect(Collectors.toMap(stat -> stat.getObject("stat").getString("name"),
                            stat -> stat.getInt("base_stat"))))
                .put("ev_yield",
                    data.getArray("stats")
                        .stream(DataArray::getObject)
                        .collect(Collectors.toMap(stat -> stat.getObject("stat").getString("name"),
                            stat -> stat.getInt("effort"))))
                .put("moves",
                    data.getArray("moves")
                        .stream(DataArray::getObject)
                        .map(move -> move.put("version_group_details", DataArray.empty()
                            .addAll(move.getArray("version_group_details")
                                .stream(DataArray::getObject)
                                .filter(version -> version.getObject("version_group").getString("name").equals("ultra-sun-ultra-moon"))
                                .collect(Collectors.toList()))))
                        .filter(move -> move.getArray("version_group_details").toList().size() != 0)
                        .collect(Collectors.toMap(move -> move.getObject("move").get("name"),
                            move -> move.getArray("version_group_details")
                                .stream(DataArray::getObject)
                                .map(method -> method.getObject("move_learn_method").get("name") + ";" + method.get("level_learned_at"))
                                .collect(Collectors.joining(";;;")))))
                .put("forms", data.getArray("forms").stream(DataArray::getObject).map(form -> form.getString("name")).collect(Collectors.toList())))
            .forEach(data -> output.print(data.toPrettyString() + ", "));
    }
}
