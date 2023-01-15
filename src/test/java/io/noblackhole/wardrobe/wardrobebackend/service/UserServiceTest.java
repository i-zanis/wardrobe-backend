package io.noblackhole.wardrobe.wardrobebackend.service;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;

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
  void findAll_shouldReturnListOfUsers() throws UserServiceException, UserNotFoundException {
    // Given
    List<User> expectedUsers = Arrays.asList(user1, user2);
    when(userRepository.findAll()).thenReturn(expectedUsers);
    // When
    List<User> actualUsers = userService.findAll();
    // Then
    assertEquals(expectedUsers, actualUsers);
  }

  @Test
  void findById_shouldReturnAUser_whenValidIdIsProvided() throws UserServiceException, UserNotFoundException {
    // Given
    User expected = user1;
    when(userRepository.findById(expected.getId())).thenReturn(Optional.of(expected));
    // When
    User actual = userService.findById(expected.getId());
    // Then
    assertEquals(expected, actual);
  }

  @Test
  void findById_shouldThrowUserServiceException_whenInvalidIdIsProvided() throws UserNotFoundException, UserServiceException {
    // Given
    Long nonExistentId = -1L;
    Long existentId = 1L;
    User expected = user1;
    when(userRepository.findById(existentId)).thenReturn(Optional.of(expected));
    // When
    User user = userService.findById(existentId);
    UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.findById(nonExistentId));
    // Then
    assertNotNull(user);
//  assertEquals("User not found with id: -1", exception.getMessage());
    assertEquals("UserNotFoundException", exception.getClass()
      .getSimpleName());
    verify(userRepository, times(1)).findById(existentId);
    verify(userRepository, times(1)).findById(nonExistentId);
  }

  @Test
  void save_shouldReturnSavedUser_whenValidUserIsProvided() throws UserServiceException, UserNotFoundException {
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
  public void testSave_WhenUserIsNull_ShouldThrowUserServiceException() {
    assertThrows(UserServiceException.class, () -> userService.save(null));
  }


  @Test
  public void testSave_WhenSaveFails_ShouldThrowUserServiceException() throws UserServiceException {
    when(userRepository.save(any(User.class))).thenThrow(new DataAccessException("Error saving user") {
    });

    assertThrows(UserServiceException.class, () -> userService.save(new User()));
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
