package pl.dawid.wishexposer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.dawid.wishexposer.domain.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findItemById(Long id); //JPA convention like a select: find item by id
    List<Item> findAllByUser_Username(String username);
    List<Item> findAllByUser_UserId(String userId);
}
