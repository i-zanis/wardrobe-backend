package io.noblackhole.wardrobe.wardrobebackend.controller;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.DuplicateEmailException;
import io.noblackhole.wardrobe.wardrobebackend.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private static final String RETRIEVING_ALL_USERS = "Retrieving all users from the DB";
  private static final String RETRIEVING_USER_BY_ID = "Retrieving user with id: {}";
  private static final String REGISTERING_USER = "Registering user: {}";

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<User>> findAll() {
    logger.info("Received request to get all users");
    try {
      List<User> users = userService.findAll();
      if (users == null || users.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(null);
      }
      return ResponseEntity.ok(users);
    } catch (DataAccessException e) {
      logger.error("Error accessing database", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(null);
    } catch (Exception e) {
      logger.error("Error getting all users", e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(null);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> findById(@PathVariable Long id) {
    logger.info("Received request to get user with id {}", id);
    try {
      User user = userService.findById(id);
      return ResponseEntity.ok(user);
    } catch (Exception e) {
      logger.error("Error getting user with id {}", id, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(null);
    }
  }

  @PostMapping("/register")
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