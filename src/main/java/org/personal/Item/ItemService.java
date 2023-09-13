package org.personal.Item;

import java.util.List;

public interface ItemService {
    List<ItemDto> getAll(long userId);
    ItemDto add(long userId, ItemDto itemDto);

    //Item findById(long itemId);
    ItemDto getById(Long itemId);


    void delete(long userId, long itemId);
}
