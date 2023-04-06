//package io.noblackhole.wardrobe.wardrobebackend.integration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.noblackhole.wardrobe.wardrobebackend.controller.ItemController;
//import io.noblackhole.wardrobe.wardrobebackend.domain.User;
//import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDtoMapper;
//import io.noblackhole.wardrobe.wardrobebackend.exception.GlobalExceptionHandler;
//import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
//import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
//import io.noblackhole.wardrobe.wardrobebackend.service.ItemService;
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
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.assertj.core.api.BDDAssertions.then;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ExtendWith(SpringExtension.class)
//public class UserDtoControllerIT {
//
//  private static final Logger logger = LoggerFactory.getLogger(IntegrationTest.class);
//  MockMvc mockMvc;
//  @Autowired
//  UserRepository userRepository;
//  @Autowired
//  UserServiceDto userServiceDto;
//  @Autowired
//  UserDtoController userDtoController;
//  User user1, user2;
//  ObjectMapper objectMapper = new ObjectMapper();
//  @Autowired
//  ItemRepository itemRepository;
//  @Autowired
//  ItemService itemService;
//  @Autowired
//  ItemController itemController;
//
//  @Autowired
//  UserDtoMapper userDtoMapper;
//
//  @BeforeEach
//  public void setUp() throws Exception {
//    userRepository.deleteAll();
//    logger.info("Setup..");
//    userServiceDto = new UserServiceDtoImpl(userRepository, userDtoMapper);
//    userDtoController = new UserDtoController(userServiceDto);
//    mockMvc = MockMvcBuilders.standaloneSetup(userDtoController, itemController, GlobalExceptionHandler.class)
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
//  void findAllUsersDto_shouldReturnAllUsersDto_expectingEmptyResult() throws Exception {
//    // Given
//
//    // When
//    ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/list/all")
//        .contentType(MediaType.APPLICATION_JSON)
//        .accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk());
//
//    // Then
//    then(userRepository.findAll()).hasSize(0);
//  }
//
//  @Test
//  void findAllUsersDto_shouldReturnAllUsersDto_expectingNonEmptyResultWithUsers() throws Exception {
//    // Given
//    userRepository.save(user1);
//    userRepository.save(user2);
//
//    // When
//    ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/v1/users/list/all")
//        .contentType(MediaType.APPLICATION_JSON)
//        .accept(MediaType.APPLICATION_JSON))
//      .andExpect(status().isOk());
//
//    // Then
//    then(response).isNotNull();
//    then(userRepository.findAll()
//      .get(0)
//      .getEmail()).isEqualTo(user1.getEmail());
//    then(userRepository.findAll()).hasSize(2);
//  }
//}
