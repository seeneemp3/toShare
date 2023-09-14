package org.personal.Item;
import org.personal.Item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    List<ItemDto> getAll(Long userId);
    ItemDto getById(Long itemId);
    ItemDto add(Long userId, ItemDto itemDto);
    ItemDto update(Long userId, Long itemId, ItemDto itemDto);
    void delete(Long userId, Long itemId);
    List<ItemDto> search(String query);
}