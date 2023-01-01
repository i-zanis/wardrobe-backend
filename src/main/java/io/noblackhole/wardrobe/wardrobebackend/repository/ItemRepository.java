package io.noblackhole.wardrobe.wardrobebackend.repository;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;


public interface ItemRepository extends CrudRepository<Item, Long> {
  Iterable<Item> findAllByUserId(Long userId);

  Iterable<Item> findAllByUserEmail(String email);

  @NonNull
  Optional<Item> findById(@NonNull Long id);
}