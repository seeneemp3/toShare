package org.personal.Item.comment;

import org.mapstruct.Mapper;

public interface CommentMapper {
    Comment  dtoToComment(CommentDto commentDto);

    CommentDto commentToDto (Comment comment);
}
