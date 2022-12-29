package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;

import java.util.List;

public interface UserService extends CrudService<User, Long> {
  User findByLastName(String lastName);

  List<User> findAllByLastName(String lastName);
}
