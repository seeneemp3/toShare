package org.personal.Item;

import lombok.RequiredArgsConstructor;
import org.personal.Item.comment.CommentDto;
import org.personal.Item.comment.CommentMapper;
import org.personal.Item.comment.CommentRepository;
import org.personal.Item.dto.ItemDto;
import org.personal.Item.dto.ItemMapper;
import org.personal.User.User;
import org.personal.User.UserRepository;
import org.personal.exeption.ItemNotFoundException;
import org.personal.exeption.UserNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("ItemServiceImpl")
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public List<ItemDto> getAll(Long ownerId) {
        return itemRepository.findByOwnerId(ownerId).stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }
    @Override
    public ItemDto getById(Long itemId) {
        return itemMapper.itemToDto(itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("No item with ID = " + itemId + " was found")));
    }
    @Override
    public ItemDto add(Long userId, ItemDto itemDto) {
        Item item = itemMapper.dtoToItem(itemDto);
        item.setOwner(getUser(userId));
        return itemMapper.itemToDto(itemRepository.save(item));
    }
    public ItemDto update(Long userId, Long itemId, ItemDto itemDto){
        Item newItem = validateBeforeUpdate(getItem(itemId), itemDto);
        newItem.setOwner(getUser(userId));
        return itemMapper.itemToDto(itemRepository.save(newItem));
    }
    @Override
    public void delete(Long userId, Long itemId) {
        itemRepository.deleteById(itemId);
    }
    public List<ItemDto> search(String keyword){
        return itemRepository.getItemsByKeyword(keyword).stream().map(itemMapper::itemToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto createComment(Long itemId, Long userId, CommentDto commentDto) {
        var comm = commentMapper.dtoToComment(commentDto);
        return  commentMapper.commentToDto(commentRepository.save(comm));
    }

    @Override
    public List<CommentDto> getCommentsByItemId(Long itemId) {
        return commentRepository.findAllByItem_Id(itemId, Sort.by(Sort.Direction.DESC, "created"))
                .stream().map(commentMapper::commentToDto).toList();
    }

    private Item validateBeforeUpdate(Item item, ItemDto itemDto) {
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
    private User getUser(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with ID = " + userId + " not found"));
    }
    private Item getItem(Long itemId){
        return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("No item with ID = " + itemId + "  was found"));
    }
}
