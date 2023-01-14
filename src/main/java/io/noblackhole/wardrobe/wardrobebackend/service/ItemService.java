package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemServiceException;

import java.util.List;


public interface ItemService {
  List<Item> findAllByUserId(Long id) throws ItemServiceException;

  Item findById(Long id) throws ItemServiceException;

  void save(Item item) throws ItemServiceException;

  void update(Item item) throws ItemServiceException;

  void deleteById(Long id) throws ItemServiceException;


}