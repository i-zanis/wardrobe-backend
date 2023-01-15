package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
  private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
  ItemRepository itemRepository;

  public ItemServiceImpl(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @Override
  public List<Item> findAllByUserId(Long id) throws ItemServiceException {
    logger.info("Finding item with userId: {}", id);
    try {
      return itemRepository.findAllByUserId(id);
    } catch (Exception e) {
      throw new ItemServiceException("Error finding items with userId: " + id, e);
    }
  }


  @Override
  public Item findById(Long id) throws ItemServiceException, ItemNotFoundException {
    logger.info("Finding item with id " + id);
    try {
      Optional<Item> item = itemRepository.findById(id);
      if (item.isPresent()) {
        return item.get();
      }
      throw new ItemNotFoundException();
    } catch (Exception e) {
      throw new ItemNotFoundException("Error finding item with id: " + id, e);
    }
  }

  @Override
  public Item save(Item item) throws ItemServiceException {
    logger.info("Saving item {} ", item);
    // @NotNull on User inside Item model caused problems - added check here
    if (item.getUser() == null) {
      throw new ItemServiceException("User is required");
    }
    try {
      return itemRepository.save(item);
    } catch (Exception e) {
      throw new ItemServiceException("Item cannot be saved", e);
    }
  }

  @Override
  public Item update(Item item) throws ItemServiceException {
    logger.info("Updating item {}", item);
    return save(item);
  }

  @Override
  public void deleteById(Long id) throws ItemServiceException {
    logger.info("Deleting item with id " + id);
    try {
      itemRepository.deleteById(id);
    } catch (Exception e) {
      throw new ItemServiceException("Item cannot be deleted", e);
    }
  }
}
