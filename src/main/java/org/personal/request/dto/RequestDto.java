package org.personal.request.dto;

import lombok.*;
import org.personal.Item.Item;
import org.personal.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RequestDto {
    private Long id;
    private String description;
    private User requester;
    private LocalDateTime created;
    private List<Item> items = new ArrayList<>();
}
