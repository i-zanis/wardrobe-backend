package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.DtoMapper;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
  private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
  ItemRepository itemRepository;
  DtoMapper itemMapper;

  public ItemServiceImpl(ItemRepository itemRepository, DtoMapper itemDtoMapper) {
    this.itemRepository = itemRepository;
    this.itemMapper = itemDtoMapper;
  }

  @Override
  public List<ItemDto> findAllByUserId(Long userId) throws ItemServiceException {
    logger.info("Finding items with userId: {}", userId);
    try {
      List<Item> items = itemRepository.findAllByUserId(userId);
      return items.stream()
        .map(itemMapper::itemToItemDto)
        .collect(Collectors.toList());
    } catch (DataAccessException e) {
      throw new ItemServiceException("Error finding items with userId: " + userId, e);
    }
  }

  @Override
  public ItemDto findById(Long id) throws ItemServiceException, ItemNotFoundException {
    logger.info("Finding item with id " + id);
    try {
      Optional<Item> item = itemRepository.findById(id);
      if (item.isPresent()) {
        return itemMapper.itemToItemDto(item.get());
      }
      throw new ItemNotFoundException();
    } catch (DataAccessException e) {
      throw new ItemNotFoundException("Error finding item with id: " + id, e);
    }
  }

  @Override
  public ItemDto save(ItemCreationDto itemCreationDto) throws ItemServiceException {
    logger.info("Saving item {} ", itemCreationDto);
    if (itemCreationDto.userId() == null) {
      throw new ItemServiceException("User is required");
    }
    try {
      Item item = itemMapper.itemCreationDtoToItem(itemCreationDto);
      Item savedItem = itemRepository.save(item);
      return itemMapper.itemToItemDto(savedItem);
    } catch (DataAccessException e) {
      throw new ItemServiceException("Item cannot be saved", e);
    }
  }


  @Override
  public ItemDto update(ItemDto itemDto) throws ItemServiceException {
    logger.info("Updating item {}", itemDto);
    if (itemDto.userId() == null) {
      throw new ItemServiceException("User is required");
    }
    try {
      Item item = itemMapper.itemDtoToItem(itemDto);
      Item savedItem = itemRepository.save(item);
      return itemMapper.itemToItemDto(savedItem);
    } catch (DataAccessException e) {
      throw new ItemServiceException("Item cannot be updated", e);
    }
  }

  @Override
  public void deleteById(Long id) throws ItemServiceException {
    logger.info("Deleting item with id " + id);
    try {
      itemRepository.deleteById(id);
    } catch (DataAccessException e) {
      throw new ItemServiceException("Item cannot be deleted", e);
    }
  }
}
