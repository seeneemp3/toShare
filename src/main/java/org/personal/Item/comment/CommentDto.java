package org.personal.Item.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.personal.Item.Item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CommentDto {
    private Long id;
    @NotEmpty(message = "Comment must not be empty")
    @NotBlank(message = "Comment must not be empty")
    private String text;
    private Item item;
    private String authorName;
    private LocalDateTime created;
}
