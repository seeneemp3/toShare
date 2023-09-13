package org.personal.Item.dto;

import org.personal.Item.Item;

public interface ItemMapper {

    Item dtoToItem(ItemDto itemDto);

    ItemDto itemToDto(Item item);

}
