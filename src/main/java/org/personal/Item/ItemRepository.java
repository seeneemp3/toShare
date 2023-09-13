package org.personal.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> getAll(Long userId);

    Item getById(Long itemId);

    Item add(Item item);

    Item update(Long userId, Item item, Long itemId);

    void delete(Long userId, Long itemId);
}
