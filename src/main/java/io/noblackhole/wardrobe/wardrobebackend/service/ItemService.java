package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemServiceException;

import java.util.List;


public interface ItemService {
  List<Item> findAllByUserId(Long id) throws ItemServiceException;

  Item findById(Long id) throws ItemServiceException, ItemNotFoundException;

  Item save(Item item) throws ItemServiceException;

  Item update(Item item) throws ItemServiceException;

  void deleteById(Long id) throws ItemServiceException;


}