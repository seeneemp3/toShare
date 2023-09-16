package org.personal.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwnerId(Long ownerId);
    @Query("SELECT i FROM Item i WHERE LOWER(i.description) LIKE LOWER(concat('%', :keyword, '%')) OR LOWER(i.name) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Item> getItemsByKeyword(@Param("keyword") String keyword);
}
