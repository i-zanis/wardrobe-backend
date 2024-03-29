package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;
import io.noblackhole.wardrobe.wardrobebackend.exception.item.ItemServiceException;

import java.util.List;

public interface ItemService {
  List<ItemDto> findAllByUserId(Long userId) throws ItemServiceException;

  ItemDto findById(Long id) throws ItemServiceException;

  ItemDto save(ItemCreationDto itemCreationDto) throws ItemServiceException;

  ItemDto update(ItemDto itemDto) throws ItemServiceException;

  void deleteById(Long id) throws ItemServiceException;
}