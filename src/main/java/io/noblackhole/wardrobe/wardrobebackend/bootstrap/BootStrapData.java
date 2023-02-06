package io.noblackhole.wardrobe.wardrobebackend.bootstrap;

import io.noblackhole.wardrobe.wardrobebackend.domain.Color;
import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.domain.Look;
import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class BootStrapData implements CommandLineRunner {
  private static final Logger logger = LoggerFactory.getLogger(BootStrapData.class);
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;

  public BootStrapData(UserRepository userRepository, ItemRepository itemRepository) {
    this.userRepository = userRepository;
    this.itemRepository = itemRepository;
  }

  @Override
  public void run(String... args) {
    logger.info("Loading bootstrap data");
    List<User> users = userRepository.findAll();
    if (users.isEmpty()) getUsers();
    logger.info("------Bootstrap data loaded-----");
    logger.info("-------------------------------");
  }

  private void getUsers() {
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

    userRepository.save(user1);
    userRepository.save(user2);
    userRepository.save(user3);
    userRepository.save(user4);

    Item item1 = new Item.Builder().withId(21L)
      .withColors(Set.of(Color.BLUE, Color.WHITE))
      .withUser(user1)
      .build();

    Item item2 = new Item.Builder().withId(22L)
      .withColors(Set.of(Color.BLUE, Color.WHITE))
      .withUser(user1)
      .build();
    Item item3 = new Item.Builder().withId(23L)
      .withColors(Set.of(Color.AQUA, Color.BEIGE))
      .withLooks(Set.of(new Look()))
      .withPrice(100.00)
      .withBrand("Nike")
      .withSize("M")
      .withUser(user1)
      .build();
    user1.getItems()
      .add(item1);
    user1.getItems()
      .add(item2);
    user1.getItems()
      .add(item3);
    userRepository.save(user1);
    List<Item> items = itemRepository.findAllByUserId(user1.getId());
    logger.info("User {} has {} {}.", user1.getFirstName(), items.size(), items.get(0));
    List<User> users = userRepository.findAll();
    logger.info("Boostrap users found: {}", users.size());
    logger.info("Boostrap items found: {}", items.size());
    List<Item> items1 = (List<Item>) itemRepository.findAll();
    for (Item item : items1) {
      logger.info("Item: {} {} {}", item, item.getId(), item.getUser()
        .getId());
    }
    logger.info("-------------------------------");
  }
}
