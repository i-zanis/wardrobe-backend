package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDtoBase;
import io.noblackhole.wardrobe.wardrobebackend.exception.user.UserNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.user.UserServiceException;

public interface UserService {
  UserDtoBase findById(Long id, Boolean isFullLoad) throws UserServiceException, UserNotFoundException;

  UserDto save(UserCreationDto userCreationDto) throws UserServiceException;

  UserDto update(UserDto userDto) throws UserServiceException;

  Boolean login(String email, String password) throws UserServiceException;

  Boolean isAuthorized(User user, String role) throws UserServiceException;

  Boolean register(User user);

  boolean isValid(User user);

}