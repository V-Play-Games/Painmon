import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;

import java.io.FileReader;
import java.io.PrintStream;
import java.util.stream.Collectors;

public class GuessDataBuilder {
    public static void main(String[] args) throws Exception {
        try (FileReader reader = new FileReader("C:\\Users\\hp\\IdeaProjects\\painmon\\pokemon.json")) {
            try (PrintStream stream = new PrintStream("guesses.json")) {
                stream.println("[");
                DataArray.fromJson(reader)
                    .stream(DataArray::getObject)
                    .map(data -> DataObject.empty()
                        .put("id", data.getInt("id"))
                        .put("name", data.getString("name"))
                        .put("sprite", data.getObject("sprites")
                            .getObject("other")
                            .getObject("official-artwork")
                            .getString("front_default"))
                        .put("types", data.getArray("types")
                            .stream(DataArray::getObject)
                            .map(type -> type.getString("name"))
                            .collect(Collectors.joining(";")))
                    )
                    .map(DataObject::toPrettyString)
                    .map(s -> s + ",")
                    .forEach(stream::println);
                stream.println("{}]");
            }
        }
    }
}
