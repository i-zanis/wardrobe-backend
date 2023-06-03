package io.noblackhole.wardrobe.wardrobebackend.repository;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import jakarta.validation.constraints.Positive;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
  List<Item> findAllByUserId(Long id);

  @NonNull
  Optional<Item> findById(@NonNull Long id);

  List<Item> findByIdIn(Collection<@Positive Long> id);
}