package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ItemService {
  List<Item> findAllByUserId(Long id) throws ItemServiceException;

  List<Item> findAllByUserEmail(String email) throws ItemServiceException;

  Item findById(Long id) throws ItemServiceException;

  void save(Item item) throws ItemServiceException;


  void deleteById(Long id) throws ItemServiceException;

}