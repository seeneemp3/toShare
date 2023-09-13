package org.personal.Item;

import lombok.Data;

@Data
public class Item {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private Boolean available;
    //private String url;
}
