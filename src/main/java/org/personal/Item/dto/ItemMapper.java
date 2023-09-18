package org.personal.Item.dto;

import org.personal.Item.Item;

public interface ItemMapper {

    Item fromDto(ItemDto itemDto);

    ItemDto toDto(Item item);

}
