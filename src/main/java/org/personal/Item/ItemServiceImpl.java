package org.personal.Item;

import lombok.RequiredArgsConstructor;
import org.personal.User.UserService;
import org.personal.User.dto.UserMapper;
import org.personal.booking.Booking;
import org.personal.booking.BookingRepository;
import org.personal.Item.comment.CommentDto;
import org.personal.Item.comment.CommentMapper;
import org.personal.Item.comment.CommentRepository;
import org.personal.Item.dto.ItemDto;
import org.personal.Item.dto.ItemMapper;
import org.personal.User.User;
import org.personal.User.UserRepository;
import org.personal.exeption.BookingDataException;
import org.personal.exeption.ItemNotFoundException;
import org.personal.exeption.UserNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//TODO check validation on endpoints
//todo remove unnecessary validation
//todo json ignore
//todo postman testing

@Service("ItemServiceImpl")
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    @Override
    public List<ItemDto> getAll(Long ownerId) {
        return itemRepository.findByOwnerId(ownerId).stream().map(itemMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ItemDto getById(Long itemId) {
        return itemMapper.toDto(itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("No item with ID = " + itemId + " was found")));
    }

    @Override
    public ItemDto add(Long userId, ItemDto itemDto) {
        Item item = itemMapper.fromDto(itemDto);
        item.setOwner(getUser(userId));
        return itemMapper.toDto(itemRepository.save(item));
    }

    public ItemDto update(Long userId, Long itemId, ItemDto itemDto) {
        Item item = getItem(itemId);
        if (!item.getOwner().getId().equals(userId)) {
            throw new UserNotFoundException("Can't update others item");
        }
        Item newItem = validateBeforeUpdate(item, itemDto);

        newItem.setOwner(getUser(userId));
        return itemMapper.toDto(itemRepository.save(newItem));
    }

    @Override
    public void delete(Long userId, Long itemId) {
        itemRepository.deleteById(itemId);
    }
    public List<ItemDto> search(String keyword){
        if(keyword.isEmpty()){
            return Collections.emptyList();
        }
        itemRepository.getItemsByKeywordNative(keyword).forEach(System.out::println);
        return itemRepository.getItemsByKeywordNative(keyword).stream().map(itemMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public CommentDto createComment(Long userId, Long itemId, CommentDto commentDto) {
        User author = userMapper.dtoToUser(userService.getUserById(userId));
        Item item = itemMapper.fromDto(getById(itemId));
        Booking booking = bookingRepository.findFirstByItem_IdAndBooker_IdAndEndIsBefore(itemId, userId, LocalDateTime.now());
        if (booking == null) {
            throw new BookingDataException("Can't leave comment without booking");
        }
        var comment = commentMapper.dtoToComment(commentDto);
        comment.setItem(item);
        comment.setAuthor(author);
        comment.setCreated(LocalDateTime.now());
        return commentMapper.commentToDto(commentRepository.save(comment));
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

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User with ID = " + userId + " not found"));
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new ItemNotFoundException("No item with ID = " + itemId + "  was found"));
    }
}
