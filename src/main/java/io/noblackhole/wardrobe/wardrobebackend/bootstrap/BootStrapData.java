package io.noblackhole.wardrobe.wardrobebackend.bootstrap;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Color;
import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.domain.Look;
import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;
import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.repository.ItemRepository;
import io.noblackhole.wardrobe.wardrobebackend.repository.LookRepository;
import io.noblackhole.wardrobe.wardrobebackend.repository.TagRepository;
import io.noblackhole.wardrobe.wardrobebackend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Component
public class BootStrapData implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger(BootStrapData.class);
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;
  private final LookRepository lookRepository;
  private final TagRepository tagRepository;

  public BootStrapData(UserRepository userRepository, ItemRepository itemRepository, LookRepository lookRepository, TagRepository tagRepository) {
    this.userRepository = userRepository;
    this.itemRepository = itemRepository;
    this.lookRepository = lookRepository;
    this.tagRepository = tagRepository;
  }

  @Override
  public void run(String... args) {
    logger.info("Loading bootstrap data");

//    if (userRepository.count() == 0) {
    createUsers();
    createLooks();
    createTags();
    createItems();
    associateItemsWithLooks();
//    }

    logger.info("Bootstrap data loaded successfully");

    for (Item i : itemRepository.findAll()) {
      logger.info("{} - Looks: {}", i.getName(), i.getLooks());

    }
  }

  private void createTags() {
    Tag tag1 = new Tag(1L, "Tag 1");
    Tag tag2 = new Tag(2L, "Tag 2");
    tagRepository.saveAll(Arrays.asList(tag1, tag2));
  }

  private void createUsers() {
    User user1 = new User.Builder().withFirstName("John")
      .withLastName("Doe")
      .withEmail("johndoe@gmail.com")
      .withPassword("password")
      .build();

    User user2 = new User.Builder().withFirstName("Jane")
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

    userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));
  }

  private void createItems() {
    Item item1 = new Item.Builder().withColors(Set.of(Color.BLUE, Color.WHITE))
      .withUser(userRepository.findById(1L)
        .get())
      .build();

    Item item2 = new Item.Builder().withColors(Set.of(Color.BLUE, Color.WHITE))
      .withUser(userRepository.findById(1L)
        .get())
      .build();

    Item item3 = new Item.Builder().withName("My Item")
      .withBrand("My Brand")
      .withSize("M")
      .withColors(Set.of(Color.BLUE, Color.WHITE))
      .withCategory(Category.TOP)
      .withUser(userRepository.findById(1L)
        .get())
      .withPrice(99.99)
      .build();

    itemRepository.saveAll(Arrays.asList(item1, item2, item3));

    // Add items to user1
    User user1 = userRepository.findById(1L)
      .get();
    user1.getItems()
      .addAll(Arrays.asList(item1, item2, item3));
    userRepository.save(user1);

    // Add items to looks
    Look look1 = lookRepository.findById(1L)
      .get();
    Look look2 = lookRepository.findById(2L)
      .get();
    look1.getItems()
      .addAll(Arrays.asList(item1, item2, item3));
    look2.getItems()
      .addAll(Arrays.asList(item2, item3));
    lookRepository.saveAll(Arrays.asList(look1, look2));

    Tag tag1 = tagRepository.findById(1L)
      .get();
    Tag tag2 = tagRepository.findById(2L)
      .get();
    tag1.getItems()
      .addAll(Arrays.asList(item1, item2, item3));
    tag2.getItems()
      .addAll(Arrays.asList(item2, item3));
    tagRepository.saveAll(Arrays.asList(tag1, tag2));
  }

  private void createLooks() {
    Look look1 = new Look.Builder().withId(555L)
      .withName("Look 1")
      .withDescription("Some description of Look 1")
      .build();

    Look look2 = new Look.Builder().withId(6666L)
      .withName("Look 2")
      .withDescription("Some description of Look 2")
      .build();

    lookRepository.saveAll(Arrays.asList(look1, look2));
  }

  private void associateItemsWithLooks() {
    // No need to create new items, just get the existing ones
    Item item1 = itemRepository.findById(1L)
      .get();
    Item item2 = itemRepository.findById(2L)
      .get();
    Item item3 = itemRepository.findById(3L)
      .get();

    // Add looks to items
    Look look1 = lookRepository.findById(1L)
      .get();
    Look look2 = lookRepository.findById(2L)
      .get();
    item1.getLooks()
      .add(look1);
    item1.getLooks()
      .add(look2);
    item2.getLooks()
      .add(look1);
    item2.getLooks()
      .add(look2);
    item3.getLooks()
      .add(look1);
    look1.getItems()
      .addAll(Arrays.asList(item1, item2, item3));
    look2.getItems()
      .addAll(Arrays.asList(item1, item2));

    // Add tags to items
    Tag tag1 = tagRepository.findById(1L)
      .get();
    Tag tag2 = tagRepository.findById(1L  ).get();
    item1.getTags()
      .add(tag1);
    item2.getTags()
      .add(tag1);
    item2.getTags()
      .add(tag2);
    item3.getTags()
      .add(tag1);
    tag1.getItems()
      .addAll(Arrays.asList(item1, item2, item3));
    tag2.getItems()
      .addAll(Arrays.asList(item1, item2));

    tag1.getItems().addAll(Arrays.asList(item1, item2, item3));
    tag2.getItems().addAll(Arrays.asList(item1, item2, item3));
    lookRepository.saveAll(Arrays.asList(look1, look2));
    tagRepository.saveAll(Arrays.asList(tag1, tag2));
    itemRepository.saveAll(Arrays.asList(item1, item2, item3));
  }
}
