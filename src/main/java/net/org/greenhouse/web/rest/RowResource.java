package net.org.greenhouse.web.rest;

import com.google.common.collect.*;
import net.org.greenhouse.domain.Row;
import net.org.greenhouse.repository.RowRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Row.
 */
@RestController
@RequestMapping("/api")
public class RowResource {

    private final Logger log = LoggerFactory.getLogger(RowResource.class);

    @Inject
    private RowRepository rowRepository;

    /**
     * POST  /rows -> Create a new row.
     */
    @RequestMapping(value = "/rows",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody Row row) throws URISyntaxException {
        log.debug("REST request to save Row : {}", row);
        if (row.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new row cannot already have an ID").build();
        }
        rowRepository.save(row);
        return ResponseEntity.created(new URI("/api/rows/" + row.getId())).build();
    }

    /**
     * PUT  /rows -> Updates an existing row.
     */
    @RequestMapping(value = "/rows",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody Row row) throws URISyntaxException {
        log.debug("REST request to update Row : {}", row);
        if (row.getId() == null) {
            return create(row);
        }
        rowRepository.save(row);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /rows -> get all the rows.
     */
    @RequestMapping(value = "/rows",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Chart>> getAll(@RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime from,
                                              @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) DateTime to)
        throws URISyntaxException {
        if (from == null) {
            from = new DateTime().minusDays(1);
        }
        if (to == null) {
            to = new DateTime();
        }
        List<Row> rows = rowRepository.findByDateTimeBetween(from, to);


        Multimap<String, Row> data = ArrayListMultimap.create();
        for (Row row : rows) {
            data.put(row.getName(), row);
        }
        List<Chart> charts = Lists.newArrayList();
        ImmutableSortedSet<String> sortedKeys = ImmutableSortedSet.copyOf(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Ordering.natural().compare(o1, o2);
            }
        }, data.keySet());
        for (String key : sortedKeys) {
            Collection<Row> keyRows = data.get(key);
            List<ImmutableList<Number>> temperature = Lists.newArrayList();
            List<ImmutableList<Number>> wetness = Lists.newArrayList();
            List<ImmutableList<Number>> wetnessGround = Lists.newArrayList();
            for (Row resultRow : keyRows) {
                if (resultRow.getDateTime() != null && resultRow.getValue() != null) {
                    if ("temperature".equals(resultRow.getType())) {
                        temperature.add(ImmutableList.of(resultRow.getDateTime().getMillis(), resultRow.getValue()));
                    } else if ("wetness".equals(resultRow.getType())) {
                        wetness.add(ImmutableList.of(resultRow.getDateTime().getMillis(), resultRow.getValue()));
                    } else if ("wetnessGround".equals(resultRow.getType())) {
                        wetnessGround.add(ImmutableList.of(resultRow.getDateTime().getMillis(), resultRow.getValue()));
                    }
                }
            }
            charts.add(new Chart(key, temperature, wetness, wetnessGround));
        }
        return new ResponseEntity<>(charts, HttpStatus.OK);
    }

    /**
     * GET  /rows/:id -> get the "id" row.
     */
    @RequestMapping(value = "/rows/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Row> get(@PathVariable String id) {
        log.debug("REST request to get Row : {}", id);
        return Optional.ofNullable(rowRepository.findOne(id))
            .map(row -> new ResponseEntity<>(
                row,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /rows/:id -> delete the "id" row.
     */
    @RequestMapping(value = "/rows/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Row : {}", id);
        rowRepository.delete(id);
    }

    private static class Chart {
        public String name;
        public List<ImmutableList<Number>> temperature;
        public List<ImmutableList<Number>> wetness;
        public List<ImmutableList<Number>> wetnessGround;

        public Chart(String name, List<ImmutableList<Number>> temperature,
                     List<ImmutableList<Number>> wetness,
                     List<ImmutableList<Number>> wetnessGround) {
            this.name = name;
            this.temperature = temperature;
            this.wetness = wetness;
            this.wetnessGround = wetnessGround;
        }
    }
}
