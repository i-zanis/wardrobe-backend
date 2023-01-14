package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
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
    try {
      return itemRepository.findAllByUserId(id);
    } catch (Exception e) {
      throw new ItemServiceException(e.getMessage());
    }
  }


  @Override
  public List<Item> findAllByUserEmail(String email) throws ItemServiceException {
    logger.info("Finding all items for user with email " + email);
    try {
      return itemRepository.findAllByUserEmail(email);
    } catch (Exception e) {
      throw new ItemServiceException(e.getMessage());
    }


  }

  @Override
  public Item findById(Long id) throws ItemServiceException {
    logger.info("Finding item with id " + id);
    try {
      Optional<Item> item = itemRepository.findById(id);
      if (item.isPresent()) {
        return item.get();
      } else {
        throw new ItemServiceException("Item not found");
      }
    } catch (Exception e) {
      throw new ItemServiceException(e.getMessage());
    }
  }

  @Override
  public void save(Item item) throws ItemServiceException {
    logger.info("Saving item " + item);
    try {
      itemRepository.save(item);
    } catch (Exception e) {
      throw new ItemServiceException(e.getMessage());
    }
  }

  @Override
  public void deleteById(Long id) throws ItemServiceException {
    logger.info("Deleting item with id " + id);
    try {
      itemRepository.deleteById(id);
    } catch (Exception e) {
      throw new ItemServiceException(e.getMessage());
    }
  }
}
