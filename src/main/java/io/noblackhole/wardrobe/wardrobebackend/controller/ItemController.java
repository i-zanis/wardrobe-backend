package io.noblackhole.wardrobe.wardrobebackend.controller;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.service.ItemService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

  private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
  private final ItemService itemService;

  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping("/all")
  public ResponseEntity<Iterable<Item>> findAll(Long userId) {
    try {
      logger.info("Received request to get all items");
      List<Item> items = itemService.findAllByUserId(userId);
      if (items == null || items.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(null);
      }
      return ResponseEntity.ok(items);
    } catch (DataAccessException e) {
      logger.error("Error accessing database", e);
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Error getting all items");
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<Item> findById(@PathVariable Long id) {
    try {
      logger.info("Received request to get item with id {}", id);
      Item item = itemService.findById(id);
      if (item == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(null);
      }
      return ResponseEntity.ok(item);
    } catch (DataAccessException e) {
      logger.error("Error accessing database", e);
      throw e;
    } catch (Exception e) {
      logger.error("Error getting item with id {}", id, e);
      throw new RuntimeException("Error getting item with id " + id);
    }
  }

  @PostMapping("/save")
  public ResponseEntity<Item> createOrUpdate(@Valid @RequestBody Item item, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(null);
    }
    try {
      itemService.save(item);
      return ResponseEntity.status(HttpStatus.CREATED)
        .body(item);
    } catch (DataAccessException e) {
      logger.error("Error accessing database", e);
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Error saving item", e);
    }
  }

}
