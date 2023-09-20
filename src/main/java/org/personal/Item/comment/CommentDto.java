package org.personal.Item.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.personal.Item.Item;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CommentDto {
    private Long id;
    @NotEmpty(message = "Comment must not be empty")
    @NotBlank(message = "Comment must not be empty")
    private String text;
    //@JsonIgnore
    private Item item;
    private String authorName;
    private LocalDateTime created;
}
