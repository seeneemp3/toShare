package org.personal.Item;

import lombok.RequiredArgsConstructor;
import org.personal.User.User;
import org.personal.User.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository repository;
    private final ItemMapper itemMapper;
    @Override
    public List<ItemDto> getAll(long userId) {
        return repository.findByUserId(userId).stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }
    @Override
    public ItemDto add(long userId, ItemDto itemDto) {
        Item item = itemMapper.dtoToItem(itemDto);
        item.setUserId(userId);
        return itemMapper.itemToDto(repository.add(item));
    }

    @Override
    public ItemDto getById(Long itemId) {
        return itemMapper.itemToDto(repository.findById(itemId));
    }

    @Override
    public void delete(long userId, long itemId) {
        repository.deleteByUserAndItemId(userId, itemId);
    }
}
