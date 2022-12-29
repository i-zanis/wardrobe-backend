package io.noblackhole.wardrobe.wardrobebackend.service;

import java.util.Set;

public interface CrudService<T, Id> {
  Set<T> findAll();

  T findById(Id id);

  T save(T o);

  void delete(T o);

  void deleteById(Id id);
}
