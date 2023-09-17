package org.personal.Item;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personal.Item.dto.ItemDto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @GetMapping
    public List<ItemDto> getAll(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Getting all items for user with ID: {}", userId);
        return itemService.getAll(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto getById(@PathVariable Long itemId) {
        log.info("Getting item by ID: {}", itemId);
        return itemService.getById(itemId);
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam(name = "text") String keyword){
        log.info("Searching items with text: {}", keyword);
        return itemService.search(keyword);
    }

    @PostMapping
    public ItemDto add(@RequestHeader("X-Sharer-User-Id") @NotNull Long userId, @RequestBody ItemDto itemDto) {
        log.info("Adding a new item for user with ID: {}", userId);
        return itemService.add(userId, itemDto);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId,@PathVariable Long itemId, @RequestBody ItemDto itemDto){
        log.debug("Updating item with ID: {} for user with ID: {}", itemId, userId);
        return itemService.update(userId, itemId, itemDto);
    }

    @DeleteMapping("/{itemId}")
    public void delete(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable long itemId) {
        log.info("Deleting item with ID: {} for user with ID: {}", itemId, userId);
        itemService.delete(userId, itemId);
    }
}
