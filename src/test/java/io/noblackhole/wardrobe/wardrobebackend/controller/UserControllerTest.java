package io.noblackhole.wardrobe.wardrobebackend.controller;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import io.noblackhole.wardrobe.wardrobebackend.service.UserService;
import io.noblackhole.wardrobe.wardrobebackend.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class UserControllerTest {
  private final TestRestTemplate testRestTemplate = new TestRestTemplate();
  UserService userService;
  @Autowired
  UserController userController;
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
  @MockBean
  private UserRepository userRepository;
  @LocalServerPort
  private int port;
  @Autowired
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    userService = new UserServiceImpl(userRepository);
    userController = new UserController(userService);
  }


  @Test
  public void findById_shouldReturnUser_Without_Password() throws Exception {
    User expectedUser = user1;
    given(userRepository.findById(expectedUser.getId())).willReturn(Optional.of(expectedUser));
    MockHttpServletRequestBuilder request = get("/v1/users/{id}", expectedUser.getId());
    mockMvc.perform(request)
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(1)))
      .andExpect(jsonPath("$.firstName", is("John")))
      .andExpect(jsonPath("$.lastName", is("Doe")))
      .andExpect(jsonPath("$.email", is("johndoe@gmail.com")))
      .andExpect(jsonPath("$.password").doesNotExist());
    ;
  }


  @Test
  void register() {
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void testFindAll() {
  }

  @Test
  void testFindById() {
  }

  @Test
  void testRegister() {
  }
}