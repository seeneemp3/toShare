package org.personal.Item;

import java.util.List;

public interface ItemService {
    List<Item> getItems(long userId);
    Item add(long userId, Item item);

    Item findById(long itemId);

    void delete(long userId, long itemId);
}
