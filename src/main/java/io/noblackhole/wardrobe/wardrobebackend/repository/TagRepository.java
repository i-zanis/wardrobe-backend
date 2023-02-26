package io.noblackhole.wardrobe.wardrobebackend.repository;

import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
  Tag findByName(String name);
}