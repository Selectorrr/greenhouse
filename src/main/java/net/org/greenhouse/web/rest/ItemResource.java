package net.org.greenhouse.web.rest;

import net.org.greenhouse.domain.Item;
import net.org.greenhouse.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Item.
 */
@RestController
@RequestMapping("/api")
public class ItemResource {

    private final Logger log = LoggerFactory.getLogger(ItemResource.class);

    @Inject
    private ItemRepository itemRepository;

    /**
     * POST  /items -> Create a new item.
     */
    @RequestMapping(value = "/items",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody Item item) throws URISyntaxException {
        log.debug("REST request to save Item : {}", item);
        if (item.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new item cannot already have an ID").build();
        }
        itemRepository.save(item);
        return ResponseEntity.created(new URI("/api/items/" + item.getId())).build();
    }

    /**
     * PUT  /items -> Updates an existing item.
     */
    @RequestMapping(value = "/items",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody Item item) throws URISyntaxException {
        log.debug("REST request to update Item : {}", item);
        if (item.getId() == null) {
            return create(item);
        }
        itemRepository.save(item);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /items -> get all the items.
     */
    @RequestMapping(value = "/items",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Item> getAll() {
        log.debug("REST request to get all Items");
        return itemRepository.findAll();
    }

    /**
     * GET  /items/:id -> get the "id" item.
     */
    @RequestMapping(value = "/items/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> get(@PathVariable String id) {
        log.debug("REST request to get Item : {}", id);
        return Optional.ofNullable(itemRepository.findOne(id))
            .map(item -> new ResponseEntity<>(
                item,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /items/:id -> delete the "id" item.
     */
    @RequestMapping(value = "/items/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable String id) {
        log.debug("REST request to delete Item : {}", id);
        itemRepository.delete(id);
    }
}
