package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserServiceException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
  Iterable<User> findAll() throws UserServiceException;

  User findById(Long id) throws UserServiceException;

  User findByEmail(String email) throws UserServiceException;

  User save(User user) throws UserServiceException;

  User update(User user) throws UserServiceException;

  Boolean login(String email, String password) throws UserServiceException;

  Boolean isAuthorized(User user, String role) throws UserServiceException;

  Boolean register(User user);

  boolean isValid(User user);

}