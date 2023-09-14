package org.personal.Item;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="items")
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "owner_id")
    private Long ownerId;
    @Column(name = "item_name")
    private String name;
    @Column
    private String description;
    @Column
    private Boolean available;
    //private String url;
}
