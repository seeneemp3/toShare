package org.personal.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> findByUserId(long userId);

    Item save(Item item);

    void deleteByUserAndItemId(long userId, long itemId);
}
