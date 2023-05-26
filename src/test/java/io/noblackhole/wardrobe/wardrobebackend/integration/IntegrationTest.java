//package io.noblackhole.wardrobe.wardrobebackend.integration;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.noblackhole.wardrobe.wardrobebackend.bootstrap.BootStrapData;
//import io.noblackhole.wardrobe.wardrobebackend.controller.ItemController;
//import io.noblackhole.wardrobe.wardrobebackend.controller.UserController;
//import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
//import io.noblackhole.wardrobe.wardrobebackend.domain.User;
//import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDtoMapper;
//import io.noblackhole.wardrobe.wardrobebackend.exception.GlobalExceptionHandler;
//import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
//import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
//import io.noblackhole.wardrobe.wardrobebackend.service.ItemService;
//import io.noblackhole.wardrobe.wardrobebackend.service.UserService;
//import io.noblackhole.wardrobe.wardrobebackend.service.UserServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith(SpringExtension.class)
//public class IntegrationTest {
//  private static final Logger logger = LoggerFactory.getLogger(IntegrationTest.class);
//  @Autowired
//  UserRepository userRepository;
//  @Autowired
//  UserService userService;
//  @Autowired
//  UserController userController;
//  MockMvc mockMvc;
//  User user1, user2;
//  ObjectMapper objectMapper = new ObjectMapper();
//  @Autowired
//  ItemRepository itemRepository;
//  @Autowired
//  ItemService itemService;
//  @Autowired
//  ItemController itemController;
//
//  UserDtoMapper userDtoMapper;
//
//  @BeforeEach
//  public void setUp() throws Exception {
//    logger.info("Setup..");
//    BootStrapData bootStrapData = new BootStrapData(userRepository, itemRepository);
//    bootStrapData.run();
//    userService = new UserServiceImpl(userRepository, userDtoMapper);
//    userController = new UserController(userService);
//    mockMvc = MockMvcBuilders.standaloneSetup(userController, itemController, GlobalExceptionHandler.class)
//      .build();
//    user1 = new User.Builder().withId(1L)
//      .withFirstName("John")
//      .withLastName("Doe")
//      .withEmail("johndoe@gmail.com")
//      .withPassword("password")
//      .build();
//    user2 = new User.Builder().withFirstName("Gemma")
//      .withLastName("Divani")
//      .withEmail("gemmadivani@gmail.com")
//      .withPassword("password")
//      .build();
//  }
//
//  @Test
//  void findAll_shouldReturnAllUsers() throws Exception {
//    User user = user1;
//    MvcResult result = mockMvc.perform(get(UserController.BASE_URL + "/all"))
//      .andDo(print())
//      .andExpect(status().isOk())
//      .andReturn();
//    List<User> actualUsers = objectMapper.readValue(result.getResponse()
//      .getContentAsString(), new TypeReference<>() {
//    });
//    assertThat(actualUsers.size()).isEqualTo(4);
//    assertThat(actualUsers.get(0)
//      .getId()).isEqualTo(user.getId());
//    assertThat(actualUsers.get(0)
//      .getFirstName()).isEqualTo(user1.getFirstName());
//    assertThat(actualUsers.get(0)
//      .getLastName()).isEqualTo(user1.getLastName());
//    assertThat(actualUsers.get(0)
//      .getEmail()).isEqualTo(user1.getEmail());
//    assertThat(actualUsers.get(0)
//      .getPassword()).isNull();
//  }
//
//  // TODO check
////  @Test
////  void testFindAll_shouldReturnsAllUsers_expectingStatusOk() throws Exception {
////    // Given
////    User user = TestUser.create1();
////
////    // When
////    ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get(UserController.BASE_URL + "/all")
////        .contentType(MediaType.APPLICATION_JSON)
////        .content(objectMapper.writeValueAsString(user))
////        .accept(MediaType.APPLICATION_JSON))
////      .andExpect(status().isOk());
////
////    // Then
////    then(response)
////      .usingRecursiveComparison()
////      .ignoringFields("id")
////      .isEqualTo(user);
////  }
//
//  @Test
//  void shouldReturnUserById() throws Exception {
//    User expectedUser = user1;
//    MvcResult result = mockMvc.perform(get(UserController.BASE_URL + "/{id}", expectedUser.getId()))
//      .andDo(print())
//      .andExpect(status().isOk())
//      .andReturn();
//    // Deserialize the response and check the user fields
//    User actualUser = objectMapper.readValue(result.getResponse()
//      .getContentAsString(), User.class);
//    assertThat(actualUser.getId()).isEqualTo(expectedUser.getId());
//    assertThat(actualUser.getFirstName()).isEqualTo(expectedUser.getFirstName());
//    assertThat(actualUser.getLastName()).isEqualTo(expectedUser.getLastName());
//    assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
//    assertThat(actualUser.getPassword()).isNull();
//  }
//
//  @Test
//  void save_shouldReturnCreatedResponseWithSavedUser() throws Exception {
//    User user = user2;
//    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    String json = objectMapper.writeValueAsString(user);
//    // Perform the request and check the response status
//    MvcResult result = mockMvc.perform(post(UserController.BASE_URL).content(json)
//        .contentType(MediaType.APPLICATION_JSON))
//      .andExpect(status().isCreated())
//      .andReturn();
//    // Deserialize the response and check the user fields
//    User savedUser = objectMapper.readValue(result.getResponse()
//      .getContentAsString(), User.class);
//    assertThat(savedUser.getId()).isNotNull();
//    assertThat(savedUser.getFirstName()).isEqualTo(user.getFirstName());
//    assertThat(savedUser.getLastName()).isEqualTo(user.getLastName());
//    assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
//    assertThat(savedUser.getPassword()).isNull();
//  }
//
//  @Test
//  void update_shouldReturnModifiedUser_whenIdIsProvided() throws Exception {
//    long userId = 1L;
//    // Get the user by id
//    MvcResult result = mockMvc.perform(get(UserController.BASE_URL + "/{id}", userId))
//      .andDo(print())
//      .andExpect(status().isOk())
//      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//      .andReturn();
//    String content = result.getResponse()
//      .getContentAsString();
//    User user = objectMapper.readValue(content, User.class);
//    // Modify the user
//    user.setFirstName("Gemma");
//    user.setLastName("Divani");
//    user.setEmail("gemmadivani@gmail.com");
//    // Convert the user to json
//    String json = objectMapper.writeValueAsString(user);
//    // Perform the update request
//    result = mockMvc.perform(put(UserController.BASE_URL + "/{id}", userId).content(json)
//        .contentType(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk())
//      .andReturn();
//    // Deserialize the response and check the user fields
//    User updatedUser = objectMapper.readValue(result.getResponse()
//      .getContentAsString(), User.class);
//    assertThat(updatedUser.getId()).isEqualTo(userId);
//    assertThat(updatedUser.getFirstName()).isEqualTo("Gemma");
//    assertThat(updatedUser.getLastName()).isEqualTo("Divani");
//    assertThat(updatedUser.getEmail()).isEqualTo("gemmadivani@gmail.com");
//  }
//
//  @Test
//  void findAllByUserId_shouldReturnAllItemsForGivenUser() throws Exception {
//    Long userId = user1.getId();
//    String url = ItemController.BASE_URL + "/all/{userId}";
//    MvcResult result = mockMvc.perform(get(url, userId))
//      .andDo(print())
//      .andExpect(status().isOk())
//      .andReturn();
//    List<Item> actualItems = objectMapper.readValue(result.getResponse()
//      .getContentAsString(), new TypeReference<>() {
//    });
//    assertThat(actualItems.size()).isEqualTo(2);
////    assertThat(actualItems.get(0)
////      .getUser()
////      .getId()).isEqualTo(userId);
//  }
//
//  @Test
//  void findById_shouldReturnItemForGivenId() throws Exception {
//    Long itemId = 1L;
//    Item expectedItem = itemService.findById(itemId);
////    String url = ItemController.BASE_URL;
//    MvcResult result = mockMvc.perform(get(ItemController.BASE_URL + "/{id}", expectedItem.getId()))
//      .andDo(print())
//      .andExpect(status().isOk())
//      .andReturn();
//    Item actualItem = objectMapper.readValue(result.getResponse()
//      .getContentAsString(), Item.class);
//    assertThat(actualItem.getId()).isEqualTo(expectedItem.getId());
//    assertThat(actualItem.getColors()).isEqualTo(expectedItem.getColors());
//    assertThat(actualItem.getNotes()).isEqualTo(expectedItem.getNotes());
////    assertThat(actualItem.getUser()).isEqualTo(expectedItem.getUser());
//  }
//
//  @Test
//  void delete_shouldReturnNoContentResponse() throws Exception {
//    Item item = itemRepository.findAll()
//      .iterator()
//      .next();
//    mockMvc.perform(delete(ItemController.BASE_URL + "/{id}", item.getId()))
//      .andExpect(status().isNoContent());
//    assertThat(itemRepository.findById(item.getId())
//      .isEmpty()).isTrue();
//  }
//}
//
//

 
