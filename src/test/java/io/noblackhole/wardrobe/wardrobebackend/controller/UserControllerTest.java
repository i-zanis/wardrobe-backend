package io.noblackhole.wardrobe.wardrobebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.noblackhole.wardrobe.wardrobebackend.TestUser;
import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.DtoMapper;
import io.noblackhole.wardrobe.wardrobebackend.exception.GlobalExceptionHandler;
import io.noblackhole.wardrobe.wardrobebackend.exception.user.UserNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.service.UserService;
import io.noblackhole.wardrobe.wardrobebackend.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
  private final ObjectMapper objectMapper = new ObjectMapper();
  @Mock
  private UserService userService;
  private User user1, user2;
  @InjectMocks
  private UserController userController;
  private MockMvc mockMvc;
  private DtoMapper userDtoMapper;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController, GlobalExceptionHandler.class)
      .build();
    user1 = new User.Builder().withId(1L)
      .withFirstName("John")
      .withLastName("Doe")
      .withEmail("johndoe@gmail.com")
      .withPassword("john-pass123")
      .build();
    user2 = new User.Builder().withId(2L)
      .withFirstName("Jane")
      .withLastName("Doe")
      .withEmail("janedoe@gmail.com")
      .withPassword("jane-pass123")
      .build();
  }

  @Test
  void shouldReturnError_WhenUserNotFound() throws Exception {
    Long nonExistingId = 55555L;
    when(userService.findById(nonExistingId, null)).thenThrow(UserNotFoundException.class);
    mockMvc.perform(get(UserController.BASE_URL + "/{id}", nonExistingId))
      .andExpect(status().isNotFound());
    verify(userService, times(1)).findById(nonExistingId, null);
  }

  @Test
  void findById_shouldReturnOk() throws Exception {
    User expectedUser = user1;
    when(userService.findById(1L, null)).thenReturn(userDtoMapper.userToUserDto(expectedUser));
    mockMvc.perform(get(UserController.BASE_URL + "/1"))
      .andExpect(status().isOk())
      .andExpect(content().json(objectMapper.writeValueAsString(expectedUser)));
    verify(userService, times(1)).findById(1L, null);
  }

  @Test
  void update_withInvalidData_shouldThrowError() throws Exception {
    User user = user1;
    String invalidName = Constants.EMPTY_STRING;
    String invalidEmail = "invalid-email";
    user.setFirstName(invalidName); // Invalid data
    user.setEmail(invalidEmail); // Invalid data
    String url = UserController.BASE_URL + "/" + user.getId();
    mockMvc.perform(put(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message", is(url)))
      .andExpect(jsonPath("$.errors.firstName", is("First name is required")))
      .andExpect(jsonPath("$.errors.email", is("Email must be valid")));
    verify(userService, times(0)).update(any());
  }

  @Test
  void save_withInvalidData_shouldThrowError() throws Exception {
    User user = user1;
    String invalidName = Constants.EMPTY_STRING;
    String invalidEmail = "invalid-email";
    user.setFirstName(invalidName); // Invalid data
    user.setEmail(invalidEmail); // Invalid data
    String url = UserController.BASE_URL;
    mockMvc.perform(post(url)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message", is(url)))
      .andExpect(jsonPath("$.errors.firstName", is("First name is required")))
      .andExpect(jsonPath("$.errors.email", is("Email must be valid")));
    verify(userService, times(0)).update(any());
  }

  @Test
  void save_validUser_shouldReturnCreatedResponseWithSavedUser() throws Exception {
    User expectedUser = TestUser.createUser1();
    when(userService.save(any())).thenReturn(userDtoMapper.userToUserDto(expectedUser));
    String json = objectMapper.writeValueAsString(expectedUser);
    mockMvc.perform(post(UserController.BASE_URL).content(json)
        .contentType(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isCreated())
      .andExpect(content().json(objectMapper.writeValueAsString(expectedUser)));
  }

  @Test
  void update_validUser_shouldReturnOkResponseWithUpdatedUser() throws Exception {
    User expectedUser = TestUser.createUser1();
    UserDto userDto = userDtoMapper.userToUserDto(expectedUser);
    when(userService.update(any())).thenReturn(userDto);
    String json = objectMapper.writeValueAsString(expectedUser);
    mockMvc.perform(put(UserController.BASE_URL + "/" + userDto.id()).content(json)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().json(objectMapper.writeValueAsString(userDto)));
  }

//  @Test
//  void shouldReturnBadRequestResponse_WhenUserInputIsInvalid() throws Exception {
//    User invalidUser = new User();
//    String json = objectMapper.writeValueAsString(invalidUser);
//    mockMvc.perform(post(UserController.BASE_URL + "/register").content(json)
//        .contentType(MediaType.APPLICATION_JSON))
//      .andExpect(status().isBadRequest());
//  }
}

