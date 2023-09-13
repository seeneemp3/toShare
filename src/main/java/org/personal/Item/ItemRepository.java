package org.personal.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> getAll(long userId);
    Item getById(long itemId);

    Item add(Item item);

    void delete(long userId, long itemId);
}
