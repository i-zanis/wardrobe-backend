package io.noblackhole.wardrobe.wardrobebackend.controller;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.UserDtoMapper;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserItemsDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserPasswordDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(UserController.BASE_URL)
public class UserController {
  public static final String BASE_URL = "/v1/users";
  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  private final UserService userService;
  private final UserDtoMapper userDtoMapper;

  public UserController(UserService userService, UserDtoMapper userDtoMapper) {
    this.userService = userService;
    this.userDtoMapper = userDtoMapper;
  }

  @GetMapping("/all")
  public List<UserDto> findAll() throws UserNotFoundException, UserServiceException {
    logger.info("Received request to get all users");
    List<User> users = userService.findAll();
    return users.stream()
      .map(userDtoMapper::userToUserDto)
      .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserDto findById(@PathVariable Long id) throws UserNotFoundException, UserServiceException {
    logger.info("Received request to get user with id {}", id);
    return userDtoMapper.userToUserDto(userService.findById(id));
  }

  @GetMapping("/load/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserItemsDto startup(@PathVariable Long id) throws UserNotFoundException, UserServiceException {
    logger.info("Received request to get user with id {}", id);
    User user = userService.findById(id);
    return userDtoMapper.userToUserItemsDto(user);
  }

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto save(@Valid @RequestBody UserPasswordDto userPasswordDto) throws DuplicateEmailException, UserServiceException {
    logger.info("Received request to save user {}", userPasswordDto);
    User user = userDtoMapper.userPasswordDtoToUser(userPasswordDto);
    return userDtoMapper.userToUserDto(userService.save(user));
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserDto update(@PathVariable Long id, @Valid @RequestBody UserDto UserDto) throws UserServiceException {
    logger.info("Received request to update user with id {}", id);
    User user = userService.update(userDtoMapper.userDtoToUser(UserDto));
    return userDtoMapper.userToUserDto(user);
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> register(@Valid @RequestBody UserPasswordDto userPasswordDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(null);
    }
    try {
      User user = userDtoMapper.userPasswordDtoToUser(userPasswordDto);
      User savedUser = userService.save(user);
      UserDto savedUserDto = userDtoMapper.userToUserDto(savedUser);
      return ResponseEntity.status(HttpStatus.CREATED)
        .body(savedUserDto);
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