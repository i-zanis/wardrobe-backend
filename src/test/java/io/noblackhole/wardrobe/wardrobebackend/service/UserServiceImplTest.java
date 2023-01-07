package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  UserRepository userRepository;
  UserService userService;
  User user1 = new User.Builder().withId(1L)
    .withFirstName("John")
    .withLastName("Doe")
    .withEmail("johndoe@gmail.com")
    .withPassword("john-pass123")
    .build();
  User user2 = new User.Builder().withId(2L)
    .withFirstName("Jane")
    .withLastName("Doe")
    .withEmail("janedoe@gmail.com")
    .withPassword("jane-pass123")
    .build();

  @BeforeEach
  void setUp() {
    userService = new UserServiceImpl(userRepository);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void findAll_shouldReturnListOfUsers() throws UserServiceException {
    // Given
    List<User> expectedUsers = Arrays.asList(user1, user2);
    when(userRepository.findAll()).thenReturn(expectedUsers);
    // When
    List<User> actualUsers = userService.findAll();
    // Then
    assertEquals(expectedUsers, actualUsers);
  }

  @Test
  void findById_shouldReturnAUser_whenValidIdIsProvided() throws UserServiceException {
    // Given
    User expected = user1;
    when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
    // When
    User actual = userService.findById(expected.getId());
    // Then
    assertEquals(expected, actual);
  }

  @Test
  void findById_shouldThrowUserServiceException_whenInvalidIdIsProvided() {
    // Given
    Long invalidId = -1L;
    // When
    UserServiceException exception = assertThrows(UserServiceException.class, () -> userService.findById(invalidId));
    // Then
    assertEquals("User id cannot be null", exception.getMessage());
  }

  @Test
  void findByEmail_shouldReturnAUser_whenValidEmailIsProvided() throws UserServiceException {
    // Given
    User expected = user1;
    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(expected));
    // When
    User actual = userService.findByEmail(user1.getEmail());
    // Then
    assertEquals(expected, actual);
  }

  @Test
  void findByEmail_shouldThrowUserServiceException_whenInvalidEmailIsProvided() {
    // Given
    String invalidEmail = "invalid@gmail.com";
    when(userRepository.findByEmail(invalidEmail)).thenReturn(Optional.empty());
    // When
    UserServiceException exception = assertThrows(UserServiceException.class, () -> userService.findByEmail(invalidEmail));
    // Then
    assertEquals("Error while retrieving user with email invalid@gmail.com from the DB", exception.getMessage());
  }

  @Test
  void save_shouldReturnSavedUser_whenValidUserIsProvided() throws UserServiceException {
    // Given
    User expected = user1;
    when(userRepository.findById(any())).thenReturn(Optional.of(user1));
    // When
    userService.save(user1);
    User actual = userService.findById(user1.getId());
    // Then
    assertEquals(expected, actual);
  }

  @Test
  void save_shouldThrowUserServiceException_whenInvalidUserIsProvided() {
    // Given
    User invalidUser = new User();
    invalidUser.setId(-1L);
    invalidUser.setEmail("invalid@gmail.com");
    // When
    UserServiceException exception = assertThrows(UserServiceException.class, () -> userService.save(invalidUser));
    // Then
    assertEquals("Error while saving user null to the DB", exception.getMessage());
  }

  @Test
  void update_shouldReturnUpdatedUser_whenValidUserIsProvided() throws UserServiceException {
    // Given
    User expected = user1;
    when(userRepository.save(any())).thenReturn(user1);
    // When
    User actual = userService.update(user1);
    // Then
    assertEquals(expected, actual);
  }

  @Test
  void update_shouldThrowUserServiceException_whenInvalidUserIsProvided() {
    // Given
    User invalidUser = new User();
    invalidUser.setId(-1L);
    invalidUser.setEmail("invalid@gmail.com");
    // When
    UserServiceException exception = assertThrows(UserServiceException.class, () -> userService.update(invalidUser));
    // Then
    assertEquals("Error while saving user null to the DB", exception.getMessage());
  }


  @Test
  void login() {
  }

  @Test
  void isAuthorized() {
  }

  @Test
  void register() {
  }

  @Test
  void isValid() {
  }
}