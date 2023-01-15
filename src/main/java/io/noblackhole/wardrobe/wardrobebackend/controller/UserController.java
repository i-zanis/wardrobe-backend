package io.noblackhole.wardrobe.wardrobebackend.controller;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.DuplicateEmailException;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserServiceException;
import io.noblackhole.wardrobe.wardrobebackend.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {
  public static final String BASE_URL = "/v1/users";
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private static final String RETRIEVING_ALL_USERS = "Retrieving all users from the DB";
  private static final String RETRIEVING_USER_BY_ID = "Retrieving user with id: {}";
  private static final String REGISTERING_USER = "Registering user: {}";

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/all")
  public List<User> findAll() throws UserNotFoundException, UserServiceException {
    logger.info("Received request to get all users");
    return userService.findAll();
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public User findById(@PathVariable Long id) throws UserNotFoundException, UserServiceException {
    logger.info("Received request to get user with id {}", id);
    return userService.findById(id);
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public User save(@Valid @RequestBody User user) throws DuplicateEmailException, UserServiceException {
    logger.info("Received request to save user {}", user);
    return userService.save(user);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public User update(@PathVariable Long id, @Valid @RequestBody User user) throws UserServiceException {
    logger.info("Received request to update user with id {}", id);
    return userService.update(user);
  }

  public ResponseEntity<User> register(@Valid @RequestBody User user, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(null);
    }
    try {
      User savedUser = userService.save(user);
      return ResponseEntity.status(HttpStatus.CREATED)
        .body(savedUser);
    } catch (Exception e) {
      if (e instanceof DuplicateEmailException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
          .build();
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .build();
      }
    }
  }

}