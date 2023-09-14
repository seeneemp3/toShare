package org.personal.Item;

import lombok.RequiredArgsConstructor;
import org.personal.Item.dto.ItemDto;
import org.personal.Item.dto.ItemMapper;
import org.personal.exeption.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("ItemServiceImpl")
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public List<ItemDto> getAll(Long userId) {
        return itemRepository.getAll(userId).stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }
    @Override
    public ItemDto getById(Long itemId) {
        return itemMapper.itemToDto(itemRepository.getById(itemId));
    }
    @Override
    public ItemDto add(Long userId, ItemDto itemDto) {
        Item item = itemMapper.dtoToItem(itemDto);
        item.setOwnerId(userId);
        return itemMapper.itemToDto(itemRepository.add(item));
    }
    public ItemDto update(Long userId, Long itemId, ItemDto itemDto){
        Item item = itemRepository.getById(itemId);
        Item newItem = validateBeforeUpdate(userId, item, itemDto);
        return itemMapper.itemToDto(itemRepository.update(userId, newItem,itemId));
    }
    @Override
    public void delete(Long userId, Long itemId) {
        itemRepository.delete(userId, itemId);
    }
    public List<ItemDto> search(String query){
        return itemRepository.search(query).stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    private Item validateBeforeUpdate(Long userId, Item item, ItemDto itemDto) {
        if (!userId.equals(item.getOwnerId())) {
            throw new UserNotFoundException("Wrong owner");
        }
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        return item;
    }
}
