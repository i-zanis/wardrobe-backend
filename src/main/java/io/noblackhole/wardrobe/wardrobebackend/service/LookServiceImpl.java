package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.domain.Look;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.look.LookDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.look.LookDtoMapper;
import io.noblackhole.wardrobe.wardrobebackend.exception.look.LookServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
import io.noblackhole.wardrobe.wardrobebackend.repository.LookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class LookServiceImpl implements LookService {
  private static final Logger logger =
    LoggerFactory.getLogger(LookServiceImpl.class);
  private final LookRepository lookRepository;
  private final LookDtoMapper mapper;
  private final ItemRepository itemRepository;

  public LookServiceImpl(LookRepository lookRepository, LookDtoMapper mapper,
                         ItemRepository itemRepository) {
    this.lookRepository = lookRepository;
    this.mapper = mapper;
    this.itemRepository = itemRepository;
  }

  @Override
  public List<LookDto> findAllByUserId(Long userId) throws LookServiceException {
    logger.info("Finding all looks for user ID: {}", userId);
    if (userId == null) {
      throw new LookServiceException("User ID is null");
    }
    try {
      List<Look> looks = lookRepository.findAllByUserId(userId);
      logger.info("Found {} looks for user ID: {}", looks.size(), userId);
      return looks.stream()
        .map(mapper::lookToLookDto)
        .collect(toList());
    } catch (Exception e) {
      throw new LookServiceException("Error finding looks for user ID: " + userId, e);
    }
  }

  @Override
  public LookDto findById(Long id) throws LookServiceException {
    logger.info("Finding look for ID: {}", id);
    if (id == null) {
      throw new LookServiceException("Look ID is null");
    }
    try {
      Optional<Look> look = lookRepository.findById(id);
      if (look.isPresent()) {
        return mapper.lookToLookDto(look.get());
      }
      throw new LookServiceException("Look not found for given ID");
    } catch (Exception e) {
      throw new LookServiceException("Error finding look for ID: " + id, e);
    }
  }

  @Transactional
  @Override
  public LookDto save(LookDto lookDto) throws LookServiceException {
    logger.info("Saving look: {}", lookDto);
    if (lookDto.id() != null) {
      throw new LookServiceException("Look ID is not null");
    }
    try {
      Look look = mapper.lookDtoToLook(lookDto);
      Set<Item> managedItems = look.getItems()
        .stream()
        .map(item -> item.getId() == null ? itemRepository.save(item) :
          itemRepository.findById(item.getId())
            .orElse(item))
        .collect(toSet());
      look.setItems(managedItems);
      managedItems.forEach(item -> item.getLooks()
        .add(look));

      Look savedLook = lookRepository.save(look);
      return mapper.lookToLookDto(savedLook);
    } catch (Exception e) {
      throw new LookServiceException("Error saving look: " + lookDto, e);
    }
  }


  @Override
  public LookDto update(LookDto lookDto) throws LookServiceException {
    logger.info("Updating look: {}", lookDto);
    if (lookDto.id() == null) {
      throw new LookServiceException("Look ID is null");
    }
    try {
      Optional<Look> existingLook = lookRepository.findById(lookDto.id());
      if (existingLook.isPresent()) {
        Look updatedLook = mapper.lookDtoToLook(lookDto);
        Look savedLook = lookRepository.save(updatedLook);
        return mapper.lookToLookDto(savedLook);
      }
      throw new LookServiceException("Look not found for given ID");
    } catch (Exception e) {
      throw new LookServiceException("Error updating look: " + lookDto, e);
    }
  }

  @Override
  public void deleteById(Long id) throws LookServiceException {
    logger.info("Deleting look for ID: {}", id);
    if (id == null) {
      throw new LookServiceException("Look ID is null");
    }
    try {
      lookRepository.deleteById(id);
    } catch (Exception e) {
      throw new LookServiceException("Error deleting look for ID: " + id, e);
    }
  }
}
