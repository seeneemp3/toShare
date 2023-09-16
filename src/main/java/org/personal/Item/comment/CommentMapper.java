package org.personal.Item.comment;

public interface CommentMapper {
    Comment  dtoToComment(CommentDto commentDto);

    CommentDto commentToDto (Comment comment);
}
