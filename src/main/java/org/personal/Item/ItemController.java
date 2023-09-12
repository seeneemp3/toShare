package org.personal.Item;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @GetMapping
    public List<Item> get(@RequestHeader("X-Later-User-Id") long userId) {
        return itemService.getItems(userId);
    }

    @PostMapping
    public Item add(@RequestHeader("X-Later-User-Id") Long userId, @RequestBody Item item) {
        return itemService.add(userId, item);
    }

    @DeleteMapping("/{itemId}")
    public void delete(@RequestHeader("X-Later-User-Id") long userId, @PathVariable long itemId) {
        itemService.delete(userId, itemId);
    }
}
