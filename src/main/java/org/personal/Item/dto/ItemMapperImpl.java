package org.personal.Item.dto;

import lombok.RequiredArgsConstructor;
import org.personal.Item.Item;
import org.personal.Item.comment.CommentMapper;
import org.personal.Item.comment.CommentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapperImpl implements ItemMapper{
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    @Override
    public Item fromDto(ItemDto itemDto) {
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
    public ItemDto toDto(Item item) {
        if (item == null) {
            return null;
        }

        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setOwner(item.getOwner());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setAvailable(item.getAvailable());
        dto.setComments(commentRepository.findAllByItem_Id(dto.getId(), Sort.unsorted())
                .stream().map(commentMapper::commentToDto).collect(Collectors.toList()));

        return dto;
    }
}
