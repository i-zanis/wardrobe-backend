package io.noblackhole.wardrobe.wardrobebackend.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.noblackhole.wardrobe.wardrobebackend.bootstrap.BootStrapData;
import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import io.noblackhole.wardrobe.wardrobebackend.service.UserService;
import io.noblackhole.wardrobe.wardrobebackend.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public class IntegrationTest {

  private static final org.slf4j.Logger logger = LoggerFactory.getLogger(IntegrationTest.class);

  @Autowired
  UserRepository userRepository;
  @Autowired
  UserService userService;
  @Autowired
  UserController userController;
  MockMvc mockMvc;
  User user1, user2;

  @BeforeEach
  public void setUp() throws Exception {
    logger.info("setUp..");
    BootStrapData bootStrapData = new BootStrapData(userRepository, userService);
    bootStrapData.run();
    userService = new UserServiceImpl(userRepository);
    userController = new UserController(userService);
    mockMvc = MockMvcBuilders.standaloneSetup(userController)
      .build();
    user1 = new User.Builder().withId(1L)
      .withFirstName("John")
      .withLastName("Doe")
      .withEmail("johndoe@gmail.com")
      .withPassword("password")
      .build();
  }

  public void tearDown() throws Exception {
    logger.info("tearDown..");
    userRepository.deleteAll();
  }

  @Test
  void shouldReturn_SavedUser() throws Exception {
    MockHttpServletRequestBuilder request = get(UserController.BASE_URL + "/{id}", user1.getId());
    mockMvc.perform(request)
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", is(1)))
      .andExpect(jsonPath("$.firstName", is(user1.getFirstName())))
      .andExpect(jsonPath("$.lastName", is(user1.getLastName())))
      .andExpect(jsonPath("$.email", is(user1.getEmail())))
      .andExpect(jsonPath("$.password").doesNotExist());
  }

  @Test
  void save_shouldReturnCreatedResponseWithSavedUser() throws Exception {
    User user = new User.Builder().withFirstName("Gemma")
      .withLastName("Divani")
      .withEmail("gemmadivani@gmail.com")
      .withPassword("password")
      .build();
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    String json = mapper.writeValueAsString(user);
    // Perform the request and check the response status
    MvcResult result = mockMvc.perform(post(UserController.BASE_URL + "/save").content(json)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andReturn();
    // Deserialize the response and check the user fields
    User savedUser = mapper.readValue(result.getResponse()
      .getContentAsString(), User.class);
    assertThat(savedUser.getId()).isNotNull();
    assertThat(savedUser.getFirstName()).isEqualTo("Gemma");
    assertThat(savedUser.getLastName()).isEqualTo("Divani");
    assertThat(savedUser.getEmail()).isEqualTo("gemmadivani@gmail.com");
    assertThat(savedUser.getPassword()).isNull();
  }

  @Test
  void update_shouldReturnModifiedUser_whenIdIsProvided() throws Exception {
    long userId = 1L;
    // Get the user by id
    MvcResult result = mockMvc.perform(get(UserController.BASE_URL + "/{id}", userId))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andReturn();
    String content = result.getResponse()
      .getContentAsString();
    ObjectMapper mapper = new ObjectMapper();
    User user = mapper.readValue(content, User.class);
    // Modify the user's properties
    user.setFirstName("Gemma");
    user.setLastName("Divani");
    user.setEmail("gemmadivani@gmail.com");
    // Send a PUT request to update the user
    mockMvc.perform(put(UserController.BASE_URL + "/{id}", user.getId()).contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(user)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.firstName", is("Gemma")))
      .andExpect(jsonPath("$.lastName", is("Divani")))
      .andExpect(jsonPath("$.email", is("gemmadivani@gmail.com")));
    // Then
    User updatedUser = userService.findById(userId);
    assertEquals(user.getFirstName(), updatedUser.getFirstName());
    assertEquals(user.getLastName(), updatedUser.getLastName());
    assertEquals(user.getEmail(), updatedUser.getEmail());
  }

}


