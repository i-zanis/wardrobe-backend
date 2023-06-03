package io.noblackhole.wardrobe.wardrobebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.DtoMapper;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.DtoMapperImpl;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
  final ObjectMapper objectMapper =
    new ObjectMapper().registerModule(new JavaTimeModule());
  @Mock
  private UserService userService;
  private User user1;
  @InjectMocks
  private UserController userController;
  private MockMvc mockMvc;
  private DtoMapper mapper;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController,
        GlobalExceptionHandler.class)
      .build();
    user1 = new User.Builder().withId(1L)
      .withFirstName("John")
      .withLastName("Doe")
      .withEmail("johndoe@gmail.com")
      .withPassword("john-pass123")
      .build();
    mapper = new DtoMapperImpl();
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
    when(userService.findById(1L, null)).thenReturn(mapper.userToUserDto(expectedUser));
    mockMvc.perform(get(UserController.BASE_URL + "/1"))
      .andExpect(status().isOk());
    verify(userService, times(1)).findById(1L, null);
  }

  @Test
  void save_withInvalidData_shouldThrowError() throws Exception {
    User user = user1;
    String invalidName = Constants.EMPTY_STRING;
    String invalidEmail = "invalid-email";
    user.setFirstName(invalidName); // Invalid data
    user.setEmail(invalidEmail); // Invalid data
    String url = UserController.BASE_URL;
    mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(user)))
      .andExpect(status().isBadRequest())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message", is(url)))
      .andExpect(jsonPath("$.errors.firstName", is("First name is required")))
      .andExpect(jsonPath("$.errors.email", is("Email must be valid")));
    verify(userService, times(0)).update(any());
  }

}

