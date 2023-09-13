package org.personal.Item;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.Item.dto.ItemDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @GetMapping
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.debug("Getting all items for user with ID: {}", userId);
        return itemService.getAll(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getById(@PathVariable Long itemId) {
        log.debug("Getting item by ID: {}", itemId);
        return itemService.getById(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam(name = "text") String text){
        log.debug("Searching items with text: {}", text);
        return itemService.search(text);
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody ItemDto itemDto) {
        log.debug("Adding a new item for user with ID: {}", userId);
        return itemService.add(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId,@PathVariable Long itemId, @RequestBody ItemDto itemDto){
        log.debug("Updating item with ID: {} for user with ID: {}", itemId, userId);
        return itemService.update(userId, itemId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    public void delete(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable long itemId) {
        log.debug("Deleting item with ID: {} for user with ID: {}", itemId, userId);
        itemService.delete(userId, itemId);
    }
}
