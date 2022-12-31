package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
  Optional<Iterable<User>> findAll() throws UserServiceException;

  Optional<User> findById(Long id) throws UserServiceException;

  Optional<User> findByEmail(String email) throws UserServiceException;

  Optional<User> save(User user) throws UserServiceException;

  Optional<User> update(User user) throws UserServiceException;

  Optional<User> login(String email, String password) throws UserServiceException;

  Optional<Boolean> isAuthorized(User user, String role) throws UserServiceException;
}