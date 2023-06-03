package io.noblackhole.wardrobe.wardrobebackend.controller;

import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDtoBase;
import io.noblackhole.wardrobe.wardrobebackend.exception.user.UserNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.user.UserServiceException;
import io.noblackhole.wardrobe.wardrobebackend.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {
  public static final String BASE_URL = "/v1/users";
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserDtoBase findById(@PathVariable Long id) throws UserNotFoundException, UserServiceException {
    logger.info("Received request to get user with id {}", id);
    return userService.findById(id, null);
  }

  // Loads Data on Login
  @GetMapping("/load/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserDtoBase findFullUserInfoOnStartup(@PathVariable Long id) throws UserNotFoundException, UserServiceException {
    logger.info("Received request to get user with id {}", id);
    return userService.findById(id, true);
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto save(@Valid @RequestBody UserCreationDto userPasswordDto) throws UserServiceException {
    logger.info("Received request to save user {}", userPasswordDto);
    return userService.save(userPasswordDto);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserDto update(@PathVariable Long id, @Valid @RequestBody UserDto userDto) throws UserServiceException {
    logger.info("Received request to update user with id {}", id);
    return userService.update(userDto);
  }

}