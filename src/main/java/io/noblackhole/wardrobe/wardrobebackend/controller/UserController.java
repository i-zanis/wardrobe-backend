package io.noblackhole.wardrobe.wardrobebackend.controller;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.DuplicateEmailException;
import io.noblackhole.wardrobe.wardrobebackend.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/users")
public class UserController {
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private static final String RETRIEVING_ALL_USERS = "Retrieving all users from the database";
  private static final String RETRIEVING_USER_BY_ID = "Retrieving user with id: {}";
  private static final String REGISTERING_USER = "Registering user: {}";

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/all")
  public ResponseEntity<Iterable<User>> findAll() {
    logger.info(RETRIEVING_ALL_USERS);
    try {
      Iterable<User> users = userService.findAll();
      return ResponseEntity.ok(users);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(null);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> findById(@PathVariable Long id) {
    logger.info(RETRIEVING_USER_BY_ID, id);
    try {
      User user = userService.findById(id);
      return ResponseEntity.ok(user);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(null);
    }
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@Valid @RequestBody User user) {
    try {
      User savedUser = userService.save(user);
      return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    } catch (Exception e) {
      if (e instanceof DuplicateEmailException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
      } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      }
    }
  }


}
//  @PutMapping("/{id}/preferences")
//  public User updatePreferences(@PathVariable Long id, @RequestBody Preferences preferences) {
//    logger.info("Updating preferences for user with id: " + id);
//    return userService.updatePreferences(id, preferences);
//  }

//  @PostMapping("/login")
//  public String login(@RequestBody LoginRequest request) {
//    logger.info("Logging in user: " + request.getEmail());
//    User user = userService.findByEmail(request.getEmail());
//    if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//      return jwtUtil.generateToken(user);
//    }
//    return userService.login(request);
//  }

// TODO register
// TODO login
// TODO user preferences

//create mappings for login, register, and preferences


