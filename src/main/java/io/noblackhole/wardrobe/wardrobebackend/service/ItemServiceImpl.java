package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.DtoMapper;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
import io.noblackhole.wardrobe.wardrobebackend.repository.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
  private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
  private final TagRepository tagRepository;
  ItemRepository itemRepository;
  DtoMapper itemDtoMapper;

  public ItemServiceImpl(ItemRepository itemRepository, DtoMapper itemDtoMapper, TagRepository tagRepository) {
    this.itemRepository = itemRepository;
    this.itemDtoMapper = itemDtoMapper;
    this.tagRepository = tagRepository;
  }

  @Override
  public List<ItemDto> findAllByUserId(Long userId) throws ItemServiceException {
    logger.info("Finding items with userId: {}", userId);
    try {
      List<Item> items = itemRepository.findAllByUserId(userId);
//      items.forEach(item -> {
//        Hibernate.initialize(item.getLooks());
//      });
      return items.stream()
        .map(itemDtoMapper::itemToItemDto)
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
        return itemDtoMapper.itemToItemDto(item.get());
      }
      throw new ItemNotFoundException();
    } catch (DataAccessException e) {
      throw new ItemNotFoundException("Error finding item with id: " + id, e);
    }
  }

  @Transactional
  @Override
  public ItemDto save(ItemCreationDto itemCreationDto) throws ItemServiceException {
    logger.info("Saving item {}", itemCreationDto);

    try {
      Item item = itemDtoMapper.itemCreationDtoToItem(itemCreationDto);

      Set<Tag> existingTags = new HashSet<>();
      Set<Tag> tagsToSave = new HashSet<>();

      for (Tag tag : item.getTags()) {
        Tag existingTag = tagRepository.findByName(tag.getName());
        if (existingTag == null) {
          tagsToSave.add(tag);
        } else {
          existingTags.add(existingTag);
        }
      }

      item.setTags(existingTags);
      tagRepository.saveAll(tagsToSave);

      Item savedItem = itemRepository.save(item);
      return itemDtoMapper.itemToItemDto(savedItem);
    } catch (DataAccessException e) {
      logger.error("Error saving item", e);
      throw new ItemServiceException("Item cannot be saved", e);
    }
  }

  @Override
  public ItemDto update(ItemDto itemDto) throws ItemServiceException {
    logger.info("Updating item {}", itemDto);
//    if (itemDto.userId() == null) {
//      throw new ItemServiceException("User is required");
//    }
    try {
      Item item = itemDtoMapper.itemDtoToItem(itemDto);
      Item savedItem = itemRepository.save(item);
      return itemDtoMapper.itemToItemDto(savedItem);
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
