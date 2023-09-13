package org.personal.Item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.personal.User.User;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;
    private Long owner;
    private String name;
    private String description;
    private Boolean available;

}
