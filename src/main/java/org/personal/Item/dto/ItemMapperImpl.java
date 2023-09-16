package org.personal.Item.dto;

import org.personal.Item.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemMapperImpl implements ItemMapper{
    @Override
    public Item dtoToItem(ItemDto itemDto) {
        if (itemDto == null) {
            return null;
        }

        Item entity = new Item();
        entity.setId(itemDto.getId());
        entity.setOwner(itemDto.getOwner());
        entity.setName(itemDto.getName());
        entity.setDescription(itemDto.getDescription());
        entity.setAvailable(itemDto.getAvailable());

        return entity;
    }

    @Override
    public ItemDto itemToDto(Item item) {
        if (item == null) {
            return null;
        }

        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setOwner(item.getOwner());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.getAvailable());

        return dto;
    }
}
