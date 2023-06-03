package io.noblackhole.wardrobe.wardrobebackend.integration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.noblackhole.wardrobe.wardrobebackend.WardrobeBackendApplication;
import io.noblackhole.wardrobe.wardrobebackend.bootstrap.BootStrapData;
import io.noblackhole.wardrobe.wardrobebackend.controller.ItemController;
import io.noblackhole.wardrobe.wardrobebackend.controller.UserController;
import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.DtoMapper;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.exception.GlobalExceptionHandler;
import io.noblackhole.wardrobe.wardrobebackend.mother.UserMother;
import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
import io.noblackhole.wardrobe.wardrobebackend.repository.LookRepository;
import io.noblackhole.wardrobe.wardrobebackend.repository.TagRepository;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import io.noblackhole.wardrobe.wardrobebackend.service.ItemService;
import io.noblackhole.wardrobe.wardrobebackend.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WardrobeBackendApplication.class})
public class IntegrationTest {
  private static final Logger logger =
    LoggerFactory.getLogger(IntegrationTest.class);

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private GlobalExceptionHandler globalExceptionHandler;
  @Autowired
  UserRepository userRepository;
  @Autowired
  UserService userService;
  @Autowired
  UserController userController;
  MockMvc mockMvc;
  User user1, user2;
  ObjectMapper objectMapper =
    new ObjectMapper().registerModule(new JavaTimeModule());
  @Autowired
  DtoMapper dtoMapper;

  @Autowired
  ItemRepository itemRepository;
  @Autowired
  ItemService itemService;
  @Autowired
  ItemController itemController;
  @Autowired
  LookRepository lookRepository;
  @Autowired
  TagRepository tagRepository;
  @Autowired
  ResourceLoader resourceLoader;

  @BeforeEach
  public void setUp() {
    logger.info("Integration test setup..");
    BootStrapData bootStrapData = new BootStrapData(userRepository,
      itemRepository, lookRepository, tagRepository, resourceLoader);
    bootStrapData.run();
    mockMvc = MockMvcBuilders.standaloneSetup(userController, itemController)
      .setControllerAdvice(globalExceptionHandler)
      .build();
    user1 = UserMother.createUser();
    user1.setId(1L);
    user2 = UserMother.createUser2();
    user2.setId(2L);
  }

  @Test
  void shouldReturnUserById() throws Exception {
    User expectedUser = user1;
    MvcResult result = mockMvc.perform(get(UserController.BASE_URL + "/{id}",
        expectedUser.getId()))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    User actualUser = objectMapper.readValue(result.getResponse()
      .getContentAsString(), User.class);
    assertThat(actualUser.getId()).isEqualTo(expectedUser.getId());
    assertThat(actualUser.getFirstName()).isEqualTo(expectedUser.getFirstName());
    assertThat(actualUser.getLastName()).isEqualTo(expectedUser.getLastName());
    assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
    assertThat(actualUser.getPassword()).isNull();
  }

  @Test
  void save_shouldReturnCreatedResponseWithSavedUser() throws Exception {
    UserCreationDto userCreationDto = dtoMapper.userToUserCreationDto(user2);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
      false);
    String json = objectMapper.writeValueAsString(userCreationDto);
    MvcResult result =
      mockMvc.perform(post(UserController.BASE_URL).content(json)
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isCreated())
      .andReturn();
    User savedUser = objectMapper.readValue(result.getResponse()
      .getContentAsString(), User.class);
    assertThat(savedUser.getId()).isNotNull();
    assertThat(savedUser.getFirstName()).isEqualTo(userCreationDto.firstName());
    assertThat(savedUser.getLastName()).isEqualTo(userCreationDto.lastName());
    assertThat(savedUser.getEmail()).isEqualTo(userCreationDto.email());
    assertThat(savedUser.getPassword()).isNull();
  }

  @Test
  void findById_shouldReturn_itemForGivenId() throws Exception {
    Long itemId = 1L;
    ItemDto expectedItem = itemService.findById(itemId);
    MvcResult result = mockMvc.perform(get(ItemController.BASE_URL + "/{id}",
        expectedItem.id()))
      .andDo(print())
      .andExpect(status().isOk())
      .andReturn();
    ItemDto actualItem = objectMapper.readValue(result.getResponse()
      .getContentAsString(), ItemDto.class);
    assertThat(actualItem.id()).isEqualTo(expectedItem.id());
    assertThat(actualItem.colors()).isEqualTo(expectedItem.colors());
    assertThat(actualItem.notes()).isEqualTo(expectedItem.notes());
    assertThat(actualItem.userId()).isEqualTo(expectedItem.userId());
  }

  @Test
  @Transactional
  void delete_shouldReturnNoContentResponse() throws Exception {
    Item item = itemRepository.findAll()
      .iterator()
      .next();
    mockMvc.perform(delete(ItemController.BASE_URL + "/{id}", item.getId()))
      .andExpect(status().isNoContent());
    item = itemRepository.findById(item.getId())
      .orElse(null);
    assertThat(item).isNull();
  }
}