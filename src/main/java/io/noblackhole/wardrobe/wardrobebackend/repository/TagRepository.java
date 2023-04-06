package io.noblackhole.wardrobe.wardrobebackend.repository;

import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
  Tag findByName(String name);

  Set<Tag> findByNameIn(Set<String> tagNames);
}