package org.personal.Item;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.personal.Item.comment.Comment;
import org.personal.User.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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
    @NotBlank(message = "The name must not be blank")
    private String name;
    @Column
    @NotNull(message = "The description must not be null")
    @Size(min = 10, max = 500, message = "The description must be between 10 and 500 characters long")
    private String description;
    @Column(nullable = false)
    @NotNull(message = "The status must not be null")
    private Boolean available;
    @Column(name = "request_id")
    private Long requestId;
}
