package net.org.greenhouse.web.rest;

import com.google.common.collect.*;
import net.org.greenhouse.domain.Value;
import net.org.greenhouse.repository.ValueRepository;
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
 * REST controller for managing Value.
 */
@RestController
@RequestMapping("/api")
public class ValueResource {

    private final Logger log = LoggerFactory.getLogger(ValueResource.class);

    @Inject
    private ValueRepository valueRepository;

    /**
     * POST  /values -> Create a new value.
     */
//    @RequestMapping(value = "/values",
//        method = RequestMethod.POST,
//        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody Value value) throws URISyntaxException {
        log.debug("REST request to save Value : {}", value);
        if (value.getType() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new value cannot already have an TYPE").build();
        }
        valueRepository.save(value);
        return ResponseEntity.created(new URI("/api/values/" + value.getType())).build();
    }

    /**
     * PUT  /values -> Updates an existing value.
     */
    @RequestMapping(value = "/values",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody Value value) throws URISyntaxException {
        log.debug("REST request to update Value : {}", value);
        value.setLastModifiedDate(DateTime.now());
        if (value.getType() == null) {
            return create(value);
        }
        valueRepository.save(value);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /values -> get all the values.
     */
    @RequestMapping(value = "/values",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Value>> getAll(@RequestParam(value = "types", required = false) List<String> types)
        throws URISyntaxException {
        List<Value> values = valueRepository.findByTypeIn(types);
        return new ResponseEntity<>(values, HttpStatus.OK);
    }

}
