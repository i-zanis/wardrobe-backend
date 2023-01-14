package io.noblackhole.wardrobe.wardrobebackend.controller;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemServiceException;
import io.noblackhole.wardrobe.wardrobebackend.service.ItemService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ItemController.BASE_URL)
public class ItemController {
  private static final String BASE_URL = "/v1/items";
  private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
  private final ItemService itemService;

  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping("/all")
  @ResponseStatus(HttpStatus.OK)
  public Iterable<Item> findAll(Long userId) throws ItemServiceException {
    logger.info("Received request to get all items");
    return itemService.findAllByUserId(userId);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Item findById(@PathVariable Long id) throws ItemServiceException {
    logger.info("Received request to get item with id {}", id);
    return itemService.findById(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) throws ItemServiceException {
    logger.info("Received request to delete item with id {}", id);
    itemService.deleteById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@PathVariable Long id, @Valid @RequestBody Item item) throws ItemServiceException {
    logger.info("Received request to update item with id {}", id);
    itemService.update(item);
  }
}
