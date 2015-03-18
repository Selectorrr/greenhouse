package net.org.greenhouse.repository;

import net.org.greenhouse.domain.Row;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Row entity.
 */
public interface RowRepository extends MongoRepository<Row, String> {
    List<Row> findByDateTimeBetween(DateTime from, DateTime to);
}
