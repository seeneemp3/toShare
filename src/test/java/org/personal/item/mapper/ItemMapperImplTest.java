package org.personal.item.mapper;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.personal.Item.Item;
import org.personal.Item.comment.Comment;
import org.personal.Item.comment.CommentDto;
import org.personal.Item.comment.CommentMapper;
import org.personal.Item.comment.CommentRepository;
import org.personal.Item.dto.ItemDto;
import org.personal.Item.dto.ItemMapperImpl;
import org.personal.User.User;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ItemMapperImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentMapper commentMapper;

    @InjectMocks
    private ItemMapperImpl itemMapper;
    private Item item;
    private ItemDto itemDto;
    private Comment comment;
    private CommentDto commentDto;
    private User owner;

    @BeforeEach
    public void setUp() {
        owner = new User();
        item = new Item(1L, owner, "Name", "Description", true, null);
        itemDto = new ItemDto(1L, owner, "Name", "Description", true, null, null);

        comment = new Comment();
        commentDto = new CommentDto();

        when(commentMapper.commentToDto(any(Comment.class))).thenReturn(commentDto);
    }

    @Test
    public void testFromDto() {
        Item result = itemMapper.fromDto(itemDto);

        assertEquals(item.getId(), result.getId());
        assertEquals(item.getOwner(), result.getOwner());
        assertEquals(item.getName(), result.getName());
        assertEquals(item.getDescription(), result.getDescription());
        assertEquals(item.getAvailable(), result.getAvailable());
    }

    @Test
    public void testToDto() {
        when(commentRepository.findAllByItem_Id(anyLong(), any())).thenReturn(Arrays.asList(comment));

        ItemDto result = itemMapper.toDto(item);

        assertEquals(item.getId(), result.getId());
        assertEquals(item.getOwner(), result.getOwner());
        assertEquals(item.getName(), result.getName());
        assertEquals(item.getDescription(), result.getDescription());
        assertEquals(item.getAvailable(), result.getAvailable());

        List<CommentDto> comments = result.getComments();
        assertEquals(1, comments.size());
        assertEquals(commentDto, comments.get(0));
    }
}
