package io.noblackhole.wardrobe.wardrobebackend.bootstrap;

import io.noblackhole.wardrobe.wardrobebackend.domain.Color;
import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.exception.ItemServiceException;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.UserServiceException;
import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import io.noblackhole.wardrobe.wardrobebackend.service.ItemService;
import io.noblackhole.wardrobe.wardrobebackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class BootStrapData implements CommandLineRunner {
  private static final Logger logger = LoggerFactory.getLogger(BootStrapData.class);
  UserRepository userRepository;
  UserService userService;
  ItemRepository itemRepository;
  ItemService itemService;

  public BootStrapData(UserRepository userRepository, UserService userService, ItemRepository itemRepository, ItemService itemService) {
    this.userRepository = userRepository;
    this.userService = userService;
    this.itemRepository = itemRepository;
    this.itemService = itemService;
  }

  @Override
  public void run(String... args) throws UserServiceException, UserNotFoundException, ItemServiceException {
    logger.info("Loading bootstrap data");
    List<User> users = new ArrayList<>();
    try {
      users = userService.findAll();
    } catch (UserNotFoundException e) {
      logger.info("No users found, creating users");
    }
    if (users.isEmpty()) getUsers();
    logger.info("Bootstrap data loaded");
    logger.info("-------------------------------");
  }

  private void getUsers() throws UserServiceException, UserNotFoundException, ItemServiceException {
    User user1 = new User.Builder().withId(1L)
      .withFirstName("John")
      .withLastName("Doe")
      .withEmail("johndoe@gmail.com")
      .withPassword("password")
      .build();

    User user2 = new User.Builder().withId(2L)
      .withFirstName("Jane")
      .withLastName("Doe")
      .withEmail("janedoe@gmail.com")
      .withPassword("password")
      .build();

    User user3 = new User.Builder().withFirstName("John")
      .withLastName("Smith")
      .withEmail("johnsmith@gmail.com")
      .withPassword("password")
      .build();

    User user4 = new User.Builder().withFirstName("Jane")
      .withLastName("Smith")
      .withEmail("janesmith@gmail.com")
      .withPassword("password")
      .build();

    userService.save(user1);
    userService.save(user2);
    userService.save(user3);
    userService.save(user4);

    Item item1 = new Item.Builder().withId(21L)
      .withColors(Set.of(Color.BLUE, Color.WHITE))
      .withUser(user1)
      .build();

    Item item2 = new Item.Builder().withId(22L)
      .withColors(Set.of(Color.BLUE, Color.WHITE))
      .withUser(user1)
      .build();
    user1.getItems().add(item1);
    user1.getItems().add(item2);
    userService.save(user1);
    List<Item> items = itemService.findAllByUserId(user1.getId());
    logger.info("User {} has {} {}.", user1.getFirstName(), items.size(), items.get(0));
    List<User> users = userService.findAll();
    logger.info("Boostrap users found: {}", users.size());
    logger.info("Boostrap items found: {}", items.size());
    List<Item> items1 = (List<Item>) itemRepository.findAll();
    for (Item item : items1) {
      logger.info("Item: {} {}", item, item.getId(), item.getUser().getId());
    }
    logger.info("-------------------------------");
  }
}
