package org.personal.Item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwnerId(Long ownerId);
//    @Query(" select i from Item i " +
//            "where lower(i.name) like lower(concat('%', :search, '%')) " +
//            " or lower(i.description) like lower(concat('%', :search, '%')) " +
//            " and i.available = true")
//    List<Item> getItemsByKeyword(@Param("search") String keyword);
@Query(value = "SELECT * FROM items i WHERE " +
        "(upper(i.item_name COLLATE \"ru_RU.UTF-8\") LIKE upper(CONCAT('%', ?1, '%') COLLATE \"ru_RU.UTF-8\") OR " +
        "upper(i.description COLLATE \"ru_RU.UTF-8\") LIKE upper(CONCAT('%', ?1, '%') COLLATE \"ru_RU.UTF-8\")) AND " +
        "i.available = TRUE", nativeQuery = true)
List<Item> getItemsByKeywordNative(String keyword);

}
