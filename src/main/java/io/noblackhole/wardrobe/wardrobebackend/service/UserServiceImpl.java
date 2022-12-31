package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
  private static final String ERROR_RETRIEVING_ALL_USERS = "Error while retrieving all users from the DB";
  private static final String ERROR_RETRIEVING_USER_BY_ID = "Error while retrieving user with id %d from the DB";
  private static final String ERROR_RETRIEVING_USER_BY_EMAIL = "Error while retrieving user with email %s from the DB";
  private static final String ERROR_SAVING_USER = "Error while saving user %s to the DB";
  private static final String ERROR_UPDATING_USER = "Error while updating user %s to the DB";
  private static final String RETRIEVING_USER_BY_ID = "Retrieving user with id: {}";
  private static final String RETRIEVING_USER_BY_EMAIL = "Retrieving user with email: {}";
  private static final String UPDATING_USER = "Updating user: {}";
  private static final String SAVING_USER = "Saving user: {}";
  private static final String RETRIEVING_ALL_USERS = "Retrieving all users from the DB";
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Optional<Iterable<User>> findAll() throws UserServiceException {
    logger.info(RETRIEVING_ALL_USERS);
    try {
      return Optional.of(userRepository.findAll());
      // Catching specific exception == better practice
    } catch (DataAccessException e) {
      throw new UserServiceException(ERROR_RETRIEVING_ALL_USERS, e);
    }
  }

  @Override
  @Cacheable(value = "users", key = "#id")
  public Optional<User> findById(Long id) throws UserServiceException {
    logger.info(RETRIEVING_USER_BY_ID, id);
    try {
      return userRepository.findById(id);
    } catch (DataAccessException e) {
      throw new UserServiceException(String.format(ERROR_RETRIEVING_USER_BY_ID, id), e);
    }
  }

  @Override
  @Cacheable(value = "users", key = "#email")
  public Optional<User> findByEmail(String email) throws UserServiceException {
    logger.info(RETRIEVING_USER_BY_EMAIL, email);
    try {
      return Optional.ofNullable(userRepository.findByEmail(email));
    } catch (DataAccessException e) {
      throw new UserServiceException(String.format(ERROR_RETRIEVING_USER_BY_EMAIL, email), e);
    }
  }

  @Transactional
  @Override
  public Optional<User> save(User user) throws UserServiceException {
    logger.info(SAVING_USER, user);
    try {
      // user.setPassword(passwordEncoder.encode(user.getPassword()));
      return Optional.of(userRepository.save(user));
    } catch (DataAccessException e) {
      throw new UserServiceException(String.format(ERROR_SAVING_USER, user), e);
    }
  }

  @Transactional
  @Override
  public Optional<User> update(User user) throws UserServiceException {
    logger.info(UPDATING_USER, user);
    try {
      return save(user);
    } catch (DataAccessException e) {
      throw new UserServiceException(String.format(ERROR_UPDATING_USER, user), e);
    }
  }

  // TODO login
  @Override
  public Optional<User> login(String email, String password) {
    return Optional.empty();
  }

  // TODO authorization
  @Override
  public Optional<Boolean> isAuthorized(User user, String role) {
    return Optional.empty();
  }
}
