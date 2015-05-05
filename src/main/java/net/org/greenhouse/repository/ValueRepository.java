package net.org.greenhouse.repository;

import net.org.greenhouse.domain.Value;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Selector on 05.05.2015.
 */
public interface ValueRepository extends MongoRepository<Value, String> {
    List<Value> findByTypeIn(Collection<String> types);
}
