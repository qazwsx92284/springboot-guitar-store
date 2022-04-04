package com.my.guitarstore.repository;

import com.my.guitarstore.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component("itemRepository")
public interface ItemRepository extends JpaRepository<Item, Long> {
}
