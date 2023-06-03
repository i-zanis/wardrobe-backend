package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.DtoMapper;
import io.noblackhole.wardrobe.wardrobebackend.exception.item.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.item.ItemServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
import io.noblackhole.wardrobe.wardrobebackend.repository.TagRepository;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class ItemServiceImpl implements ItemService {
  private static final Logger logger =
    LoggerFactory.getLogger(ItemServiceImpl.class);
  private final TagRepository tagRepository;
  ItemRepository itemRepository;
  DtoMapper mapper;

  public ItemServiceImpl(ItemRepository itemRepository, DtoMapper mapper,
                         TagRepository tagRepository) {
    this.itemRepository = itemRepository;
    this.mapper = mapper;
    this.tagRepository = tagRepository;
  }

  @Override
  public List<ItemDto> findAllByUserId(Long userId) throws ItemServiceException {
    logger.info("Finding items with userId: {}", userId);
    try {
      List<Item> items = itemRepository.findAllByUserId(userId);
      items.forEach(item -> Hibernate.initialize(item.getLooks()));
      return items.stream()
        .map(mapper::itemToItemDto)
        .collect(toList());
    } catch (DataAccessException e) {
      throw new ItemServiceException("Error finding items with userId: " + userId, e);
    }
  }

  @Override
  public ItemDto findById(Long id) throws ItemServiceException {
    logger.info("Finding item with id {}", id);
    try {
      Optional<Item> item = itemRepository.findById(id);
      if (item.isPresent()) {
        return mapper.itemToItemDto(item.get());
      }
      throw new ItemNotFoundException();
    } catch (DataAccessException e) {
      throw new ItemNotFoundException("Error finding item with id: " + id, e);
    }
  }

  @Transactional
  @Override
  public ItemDto save(ItemCreationDto itemCreationDto)
    throws ItemServiceException {
    logger.debug("Saving item {}", itemCreationDto);
    try {
      Item item = mapper.itemCreationDtoToItem(itemCreationDto);
      Set<Tag> combinedTags = processTags(item);
      item.setTags(combinedTags);
      Item savedItem = itemRepository.save(item);
      return mapper.itemToItemDto(savedItem);
    } catch (DataAccessException e) {
      logger.error("Error saving item", e);
      throw new ItemServiceException("Item cannot be saved", e);
    }
  }

  private Set<Tag> processTags(Item item) {
    Set<String> tagNames = extractTagNames(item);
    Map<String, Tag> existingTagsMap = findExistingTags(tagNames);
    Set<Tag> newTags = findNewTags(item, existingTagsMap);
    tagRepository.saveAll(newTags);
    return combineTags(item, existingTagsMap);
  }

  private Set<String> extractTagNames(Item item) {
    return item.getTags()
      .stream()
      .map(Tag::getName)
      .collect(toSet());
  }

  private Map<String, Tag> findExistingTags(Set<String> tagNames) {
    Set<Tag> existingTags = tagRepository.findByNameIn(tagNames);
    return existingTags.stream()
      .collect(Collectors.toMap(Tag::getName, Function.identity()));
  }

  private Set<Tag> findNewTags(Item item, Map<String, Tag> existingTagsMap) {
    return item.getTags()
      .stream()
      .filter(tag -> !existingTagsMap.containsKey(tag.getName()))
      .collect(toSet());
  }

  private Set<Tag> combineTags(Item item, Map<String, Tag> existingTagsMap) {
    return item.getTags()
      .stream()
      .map(tag -> existingTagsMap.getOrDefault(tag.getName(), tag))
      .collect(toSet());
  }


  @Override
  public ItemDto update(ItemDto itemDto) throws ItemServiceException {
    logger.info("Updating item {}", itemDto);
    if (itemDto.userId() == null) {
      throw new ItemServiceException("User is required");
    }
    try {
      Item item = mapper.itemDtoToItem(itemDto);
      Item savedItem = itemRepository.save(item);
      return mapper.itemToItemDto(savedItem);
    } catch (DataAccessException e) {
      throw new ItemServiceException("Item cannot be updated", e);
    }
  }

  @Override
  public void deleteById(Long id) throws ItemServiceException {
    logger.info("Deleting item with id {}", id);
    try {
      itemRepository.deleteById(id);
    } catch (DataAccessException e) {
      throw new ItemServiceException("Item cannot be deleted", e);
    }
  }
}
