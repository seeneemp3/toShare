package org.personal.item.comment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.personal.Item.comment.Comment;
import org.personal.Item.comment.CommentDto;
import org.personal.Item.comment.CommentMapperImpl;
import org.personal.User.User;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CommentMapperTest {
    private CommentMapperImpl commentMapper;

    @BeforeEach
    public void setUp() {
        commentMapper = new CommentMapperImpl();
    }

    @Test
    public void testDtoToComment() {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setText("Test text");
        commentDto.setCreated(LocalDateTime.of(2023, Month.FEBRUARY, 21, 12, 30));
        commentDto.setAuthorName("Author");

        Comment comment = commentMapper.dtoToComment(commentDto);

        assertEquals(1L, comment.getId());
        assertEquals("Test text", comment.getText());
        assertEquals(LocalDateTime.of(2023, Month.FEBRUARY, 21, 12, 30), comment.getCreated());
        assertEquals("Author", comment.getAuthor().getName());
    }

    @Test
    public void testDtoToComment_Null() {
        Comment comment = commentMapper.dtoToComment(null);
        assertNull(comment);
    }

    @Test
    public void testCommentToDto() {
        User user = new User();
        user.setName("Author");

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Test text");
        comment.setCreated(LocalDateTime.of(2023, Month.FEBRUARY, 21, 12, 30));
        comment.setAuthor(user);

        CommentDto commentDto = commentMapper.commentToDto(comment);

        assertEquals(1L, commentDto.getId());
        assertEquals("Test text", commentDto.getText());
        assertEquals(LocalDateTime.of(2023, Month.FEBRUARY, 21, 12, 30), commentDto.getCreated());
        assertEquals("Author", commentDto.getAuthorName());
    }

    @Test
    public void testCommentToDto_Null() {
        CommentDto commentDto = commentMapper.commentToDto(null);
        assertNull(commentDto);
    }
}


