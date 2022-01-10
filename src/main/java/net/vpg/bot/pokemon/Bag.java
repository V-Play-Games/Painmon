package net.vpg.bot.pokemon;

import net.dv8tion.jda.api.utils.data.DataObject;
import net.dv8tion.jda.api.utils.data.SerializableData;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.stream.Collectors;

public class Bag implements SerializableData {
    private final DataObject data;
    private final Map<Item, Integer> items;

    public Bag(DataObject data) {
        this.data = data;
        items = data.toMap()
            .entrySet()
            .stream()
            .collect(Collectors.toMap(e -> Item.get(e.getKey()), e -> (Integer) e.getValue()));
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void incrementItemCount(String id) {
        addItemCount(id, 1);
    }

    public void decrementItemCount(String id) {
        addItemCount(id, -1);
    }

    public void addItemCount(String id, int delta) {
        Item item = Item.get(id);
        Integer count = items.getOrDefault(item, 0);
        items.put(item, count + delta);
    }

    public void setItemCount(String id, int value) {
        items.put(Item.get(id), value);
    }

    @Nonnull
    @Override
    public DataObject toData() {
        return data;
    }
}
