package io.noblackhole.wardrobe.wardrobebackend.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.noblackhole.wardrobe.wardrobebackend.controller.UserController;
import io.noblackhole.wardrobe.wardrobebackend.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
  @Mock
  private UserService userService;

  @InjectMocks
  private UserController userController;

  @BeforeEach
  void setUp() {
  }

  @Test
  void findAll() throws UserServiceException {
    // Set up the mock service to return a list of users
    List<User> expectedUsers = Arrays.asList(new User(), new User());
    when(userService.findAll()).thenReturn(Optional.of(expectedUsers));

    // Call the controller's findAll method and verify it returns the expected list
    List<User> returnedUsers = (List<User>) userController.findAll();
    assertEquals(expectedUsers, returnedUsers);

    // Verify that the service's findAll method was called
    verify(userService).findAll();
  }
}