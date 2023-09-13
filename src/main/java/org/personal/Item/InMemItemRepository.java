package org.personal.Item;

import org.personal.exeption.ItemNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemItemRepository implements ItemRepository{
    private final Map<Long, List<Item>> items = new HashMap<>();

    public List<Item> getAll(long userId) {
        return items.getOrDefault(userId, Collections.emptyList());
    }
    public Item getById(long itemId){
        return items.values().stream()
                .flatMap(List::stream)
                .filter(id -> id.getId().equals(itemId))
                .findAny()
                .orElseThrow(() -> new ItemNotFoundException("Item with ID = " + itemId + " not found!"));
   }
    public Item add(Item item) {
        item.setId(getId());
        items.compute(item.getUserId(), (userId, userItems) -> {
            if(userItems == null) {
                userItems = new ArrayList<>();
            }
            userItems.add(item);
            return userItems;
        });
        return item;
    }
    public void delete(long userId, long itemId) {
        if (items.containsKey(userId)){
            List<Item> list = items.get(userId);
            list.removeIf(i -> i.getId().equals(itemId));
        }
    }
    private long getId() {
        long lastId = items.values()
                .stream()
                .flatMap(Collection::stream)
                .mapToLong(Item::getId)
                .max()
                .orElse(0);
        return lastId + 1;
    }
}
