package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemServiceException;

public interface ItemService {
  Iterable<Item> findAllByUserId(Long id) throws ItemServiceException;

  Iterable<Item> findAllByUserEmail(String email) throws ItemServiceException;

  Item findById(Long id) throws ItemServiceException;

  void save(Item item) throws ItemServiceException;


  void deleteById(Long id) throws ItemServiceException;
}