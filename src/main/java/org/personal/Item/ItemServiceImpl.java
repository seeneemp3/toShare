package org.personal.Item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository repository;
    @Override
    public Item add(long userId, Item item) {
        item.setUserId(userId);
        return repository.add(item);
    }
    public Item findById(long itemId){
        repository
    }

    @Override
    public List<Item> getItems(long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public void delete(long userId, long itemId) {
        repository.deleteByUserAndItemId(userId, itemId);
    }
}
