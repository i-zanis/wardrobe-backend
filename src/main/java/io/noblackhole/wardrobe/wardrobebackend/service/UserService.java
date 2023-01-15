package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
  List<User> findAll() throws UserServiceException, UserNotFoundException;

  User findById(Long id) throws UserServiceException, UserNotFoundException;


  User save(User user) throws UserServiceException;

  User update(User user) throws UserServiceException;

  Boolean login(String email, String password) throws UserServiceException;

  Boolean isAuthorized(User user, String role) throws UserServiceException;

  Boolean register(User user);

  boolean isValid(User user);

}