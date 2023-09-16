package org.personal.Item;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.personal.User.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name="items")
@NoArgsConstructor
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    @Column(name = "item_name")
    private String name;
    @Column
    private String description;
    @Column(nullable = false)
    @NotNull
    private Boolean available;
}
