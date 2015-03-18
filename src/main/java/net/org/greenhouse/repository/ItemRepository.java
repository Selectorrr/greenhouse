package net.org.greenhouse.repository;

import net.org.greenhouse.domain.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Item entity.
 */
public interface ItemRepository extends MongoRepository<Item,String> {

}
