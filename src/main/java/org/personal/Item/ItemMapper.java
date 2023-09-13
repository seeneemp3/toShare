package org.personal.Item;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item dtoToItem(ItemDto itemDto);

    ItemDto itemToDto(Item item);

}
