package org.personal.Item.comment;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment  dtoToComment(CommentDto commentDto);

    CommentDto commentToDto (Comment comment);
}
