package org.personal.Item.dto;

import org.mapstruct.Mapper;
import org.personal.Item.Item;

public interface ItemMapper {

    Item fromDto(ItemDto itemDto);

    ItemDto toDto(Item item);
}
