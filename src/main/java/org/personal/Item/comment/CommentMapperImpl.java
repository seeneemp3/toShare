package org.personal.Item.comment;

import org.personal.Item.Item;
import org.personal.User.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapperImpl implements CommentMapper{
        public Comment dtoToComment(CommentDto commentDto) {
            if (commentDto == null) {
                return null;
            }

            Comment comment = new Comment();
            comment.setId(commentDto.getId());
            comment.setText(commentDto.getText());
            comment.setCreatedTime(commentDto.getCreated());


            Item item = new Item();
            item.setId(commentDto.getId());
            comment.setItem(item);

            User user = new User();
            user.setName(commentDto.getAuthorName());
            comment.setAuthor(user);

            return comment;
        }

        public CommentDto commentToDto(Comment comment) {
            if (comment == null) {
                return null;
            }

            CommentDto commentDto = new CommentDto();
            commentDto.setId(comment.getId());
            commentDto.setText(comment.getText());
            commentDto.setCreated(comment.getCreatedTime());
            commentDto.setItem(comment.getItem());
            commentDto.setAuthorName(comment.getAuthor().getName());

            return commentDto;
        }
    }


