package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemServiceException;

import java.util.List;

public interface ItemService {
  List<ItemDto> findAllByUserId(Long userId) throws ItemServiceException;

  ItemDto findById(Long id) throws ItemServiceException, ItemNotFoundException;

  ItemDto save(ItemCreationDto itemCreationDto) throws ItemServiceException;

  ItemDto update(ItemDto itemDto) throws ItemServiceException, ItemNotFoundException;

  void deleteById(Long id) throws ItemServiceException;
}