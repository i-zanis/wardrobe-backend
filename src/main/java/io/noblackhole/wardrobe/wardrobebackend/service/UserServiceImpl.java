package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.DtoMapper;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDtoBase;
import io.noblackhole.wardrobe.wardrobebackend.exception.user.UserNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.user.UserServiceException;
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
  private final DtoMapper dtoMapper;

  public UserServiceImpl(UserRepository userRepository, DtoMapper dtoMapper) {
    this.userRepository = userRepository;
    this.dtoMapper = dtoMapper;
  }

  @Override
  @Cacheable(value = "users", key = "#id")
  public UserDtoBase findById(Long id, Boolean isFullLoad) throws UserServiceException, UserNotFoundException {
    logger.info(RETRIEVING_USER_BY_ID, id);
    try {
      Optional<User> user = userRepository.findById(id);
      if (user.isPresent()) {
        logger.info("Found user: {}", user);
        if (isFullLoad != null && isFullLoad) {
          return dtoMapper.userToUserItemsDto(user.get());
        }
        return dtoMapper.userToUserDto(user.get());
      }
      throw new UserNotFoundException();
    } catch (DataAccessException e) {
      throw new UserServiceException(String.format(ERROR_RETRIEVING_USER_BY_ID, id), e);
    }
  }

  @Transactional
  @Override
  public UserDto save(UserCreationDto userPasswordDto) throws UserServiceException {
    logger.info(SAVING_USER, userPasswordDto);
    validateUserNotNull(userPasswordDto);
    try {
      User user = dtoMapper.userCreationDtoToUser(userPasswordDto);
      user = userRepository.save(user);
      return dtoMapper.userToUserDto(user);
    } catch (DataAccessException e) {
      throw new UserServiceException(String.format(ERROR_SAVING_USER, userPasswordDto), e);
    }
  }

  @Transactional
  @Override
  public UserDto update(UserDto userDto) throws UserServiceException {
    logger.info(UPDATING_USER, userDto);
    validateUserNotNull(userDto);
    User user = dtoMapper.userDtoToUser(userDto);
    try {
      user = userRepository.save(user);
      return dtoMapper.userToUserDto(user);
    } catch (DataAccessException e) {
      throw new UserServiceException(String.format(ERROR_UPDATING_USER, userDto), e);
    }
  }

  void validateUserNotNull(Object object) throws UserServiceException {
    if (object == null) {
      throw new UserServiceException("User cannot be null");
    }
  }

  // TODO login
  @Override
  public Boolean login(String email, String password) {
    return null;
  }

  // TODO authorization
  @Override
  public Boolean isAuthorized(User user, String role) {
    return false;
  }

  @Override
  public Boolean register(User user) {
    return null;
  }

  @Override
  public boolean isValid(User user) {
    return false;
  }
}
