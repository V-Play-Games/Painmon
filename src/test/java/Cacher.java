import net.dv8tion.jda.api.utils.data.DataArray;
import net.dv8tion.jda.api.utils.data.DataObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.io.PrintStream;
import java.util.concurrent.atomic.AtomicInteger;

public class Cacher {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String BASE_URL = "https://pokeapi.co/api/v2/";
        // noinspection
        for (String endpoint : new String[]{/*"ability", "move",*/ "pokemon"}) {
            try (PrintStream output = new PrintStream(endpoint + ".json")) {
                output.println("[");
                Request.Builder builder = new Request.Builder();
                DataObject data = DataObject.fromJson(client.newCall(builder.url(BASE_URL + endpoint + "/?limit=-1").build()).execute().body().string());
                int count = data.getInt("count");
                AtomicInteger i = new AtomicInteger();
                data.getArray("results")
                    .stream(DataArray::getObject)
                    .map(obj -> obj.getString("url"))
                    .peek(url -> System.out.println("Progress: " + i.incrementAndGet() + "/" + count + " | GET /" + url.replace(BASE_URL, "")))
                    .map(builder::url)
                    .map(Request.Builder::build)
                    .map(client::newCall)
                    .map(o -> {
                        try (ResponseBody body = o.execute().body()) {
                            return body.string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .map(DataObject::fromJson)
                    .map(DataObject::toPrettyString)
                    .map(s -> s + ",")
                    .forEach(output::println);
                output.println("{}]");
            }
        }
    }
}
