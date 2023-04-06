package io.noblackhole.wardrobe.wardrobebackend.controller;

import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;
import io.noblackhole.wardrobe.wardrobebackend.exception.item.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.item.ItemServiceException;
import io.noblackhole.wardrobe.wardrobebackend.service.ItemService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ItemController.BASE_URL)
public class ItemController {
  public static final String BASE_URL = "/v1/items";
  private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
  private final ItemService itemService;

  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping("/all/{userId}")
  @ResponseStatus(HttpStatus.OK)
  public Iterable<ItemDto> findAll(@PathVariable Long userId) throws ItemServiceException {
    logger.info("Received request to get all items");
    return itemService.findAllByUserId(userId);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ItemDto findById(@PathVariable Long id) throws ItemServiceException, ItemNotFoundException {
    logger.info("Received request to get item with id {}", id);
    return itemService.findById(id);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Long id) throws ItemServiceException {
    logger.info("Received request to delete item with id {}", id);
    itemService.deleteById(id);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public ItemDto update(@PathVariable Long id, @Valid @RequestBody ItemDto itemDto) throws ItemServiceException, ItemNotFoundException {
    logger.info("Received request to update item with id {}", id);
    return itemService.update(itemDto);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ItemDto save(@Valid @RequestBody ItemCreationDto itemCreationDto) throws ItemServiceException {
    logger.info("Saving item {}", itemCreationDto);
//    logger.debug("Received request to create item: {}", itemCreationDto);
    Byte[] imageData = itemCreationDto.imageData();
//    for (var tag : itemCreationDto.tags()) {
//      logger.info("Tag: {}", tag.getName());
//    }
    return itemService.save(itemCreationDto);
  }
}
