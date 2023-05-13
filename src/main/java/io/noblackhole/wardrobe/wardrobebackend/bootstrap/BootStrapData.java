package io.noblackhole.wardrobe.wardrobebackend.bootstrap;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
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
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class BootStrapData implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger(BootStrapData.class);
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;
  private final LookRepository lookRepository;
  private final TagRepository tagRepository;
  private final Map<String, Byte[]> images = new HashMap<>();
  private final ResourceLoader resourceLoader;

  public BootStrapData(UserRepository userRepository,
                       ItemRepository itemRepository,
                       LookRepository lookRepository,
                       TagRepository tagRepository,
                       ResourceLoader resourceLoader) {
    this.userRepository = userRepository;
    this.itemRepository = itemRepository;
    this.lookRepository = lookRepository;
    this.tagRepository = tagRepository;
    this.resourceLoader = resourceLoader;
  }

  @Override
  public void run(String... args) {
    try {
      images.put("image1", loadImage("barbor-tack-fedora-hot-navy.png"));
      images.put("image2", loadImage("bvlgari-sunglasses.png"));
      images.put("image3", loadImage("red-white-jumper.png"));
      images.put("image4", loadImage("earrings.png"));
      images.put("image5", loadImage("black-jeans.png"));
      images.put("image6", loadImage("beige-shoes.png"));
      images.put("look1", loadImage("look1.png"));
      images.put("image7", loadImage("channel-shoes.png"));
      images.put("image8", loadImage("pleated-skirt.png"));
      images.put("image9", loadImage("golden-watch.png"));
      images.put("image10", loadImage("miessial-dress.png"));
      images.put("look2", loadImage("look2.png"));
    } catch (IOException e) {
      logger.error("Error loading images: ", e);
    }

    if (userRepository.count() == 0) {
      createUsers();
      createLooks();
      createTags();
      createItems();
      associateItemsWithLooks();
    }

    logger.info("Bootstrap data loaded successfully");

    for (Item i : itemRepository.findAll()) {
      logger.info("{} - Looks: {}", i.getName(), i.getLooks());

    }
  }

  private void createTags() {
    Tag tag1 = new Tag.Builder().withId(1L)
      .withName("Hat")
      .build();
    Tag tag2 = new Tag.Builder().withId(2L)
      .withName("Work")
      .build();
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

    Item item1 = new Item.Builder().withName("Navy Hat")
      .withBrand("Barbor")
      .withSize("S")
      .withColors(Set.of("Navy"))
      .withNotes("Barbor Tack Fedora Hot Navy")
      .withCategory(Category.ACCESSORIES)
      .withUser(userRepository.findById(1L)
        .get())
      .withImageData(images.get("image1"))
      .build();

    Item item2 = new Item.Builder().withName("Black sunglasses")
      .withBrand("Bvlgari")
      .withSize("")
      .withColors(Set.of("Black"))
      .withNotes("Bvlgari BV 6083B 2014/8G")
      .withCategory(Category.ACCESSORIES)
      .withUser(userRepository.findById(1L)
        .get())
      .withImageData(images.get("image2"))
      .build();

    Item item3 = new Item.Builder()
      .withName("Picasso Rouge")
      .withBrand("The Breton Shirt")
      .withSize("M")
      .withColors(Set.of("White", "Red"))
      .withCategory(Category.TOP)
      .withImageData(images.get("image3"))
      .withUser(userRepository.findById(1L)
        .get())
      .withPrice(29.0)
      .build();

    Item item4 = new Item.Builder()
      .withName("Earrings")
      .withBrand("Hermes")
      .withSize("")
      .withColors(Set.of("Gold"))
      .withCategory(Category.ACCESSORIES)
      .withImageData(images.get("image4"))
      .withUser(userRepository.findById(1L)
        .get())
      .withPrice(1151.2)
      .build();

    Item item5 = new Item.Builder().
      withName("Black Jeans")
      .withBrand("Levi's")
      .withSize("UK 10")
      .withColors(Set.of("Black"))
      .withCategory(Category.BOTTOM)
      .withImageData(images.get("image5"))
      .withUser(userRepository.findById(1L)
        .get())
      .build();

    Item item6 = new Item.Builder().
      withName("Beige Shoes")
      .withBrand("Zara")
      .withSize("UK 8")
      .withColors(Set.of("Beige"))
      .withCategory(Category.SHOES)
      .withImageData(images.get("image6"))
      .withUser(userRepository.findById(1L)
        .get())
      .build();

    Item item7 = new Item.Builder().withName("Black & Beige Heels")
      .withBrand("Chanel")
      .withSize("UK 8, 24.3 cm")
      .withColors(Set.of("Black", "Beige"))
      .withNotes("Similar design at XOXO fit size at UK 37")
      .withCategory(Category.SHOES)
      .withUser(userRepository.findById(1L)
        .get())
      .withImageData(images.get("image7"))
      .build();

    Item item8 = new Item.Builder().withName("Pleated Skirt")
      .withBrand("Whistles")
      .withSize("UK 10")
      .withPrice(52.99)
      .withCategory(Category.BOTTOM)
      .withColors(Set.of("Brown"))
      .withImageData(images.get("image8"))
      .withNotes("""
        Similar design at Zara fit size at UK 10. \nGood for work and
        casual wear.
        """)
      .withUser(userRepository.findById(1L)
        .get())
      .build();

    Item item9 = new Item.Builder()
      .withBrand("Holuns")
      .withName("Gold Watch")
      .withSize("")
      .withPrice(2000.99)
      .withCategory(Category.ACCESSORIES)
      .withColors(Set.of("Gold"))
      .withImageData(images.get("image9"))
      .withUser(userRepository.findById(1L)
        .get())
      .build();

    Item item10 = new Item.Builder()
      .withBrand("Miessial")
      .withName("Ruffle cap sleeve dress")
      .withSize("UK 10")
      .withPrice(150.20)
      .withCategory(Category.TOP)
      .withColors(Set.of("Light Blue"))
      .withImageData(images.get("image10"))
      .withUser(userRepository.findById(1L)
        .get())
      .withNotes("""
        Similar design at Amazon brand fit size at UK 10. \nGood for\s
        going out and casual wear.
        """)
      .build();

    itemRepository.saveAll(Arrays.asList(item1, item2, item10, item4, item5,
      item6, item7, item9, item8, item3));

    // Add items to user1
    User user1 = userRepository.findById(1L)
      .get();
    user1.getItems()
      .addAll(Arrays.asList(item1, item2, item3, item4, item5, item6, item7,
        item8, item9, item10));
    userRepository.save(user1);

    // Add items to looks
//    Look look1 = lookRepository.findById(1L)
//      .get();
//    Look look2 = lookRepository.findById(2L)
//      .get();
//    look1.getItems()
//      .addAll(Arrays.asList(item1, item2, item3, item4, item5, item6));
//    look2.getItems()
//      .addAll(Arrays.asList(item8, item9, item10));
//    lookRepository.saveAll(Arrays.asList(look1, look2));

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
    Tag tag1 = new Tag.Builder().withId(1L)
      .withName("Spring-Summer")
      .build();
    Tag tag2 = new Tag.Builder().withId(2L)
      .withName("Shopping")
      .build();
    Tag tag3 = new Tag.Builder().withId(3L)
      .withName("Casual")
      .build();
    tagRepository.saveAll(Arrays.asList(tag1, tag2, tag3));
    Look look1 = new Look.Builder().withId(555L)
      .withName("Casual")
      .withDescription("Beautiful day look for spring-summer")
      .withLookImageData(images.get("look1"))
      .build();

    Look look2 = new Look.Builder().withId(6666L)
      .withName("Countryside")
      .withLookImageData(images.get("look2"))
      .withDescription("Out in the fields wearing a beautiful dress")
      .build();

    lookRepository.saveAll(Arrays.asList(look1, look2));
  }

  private void associateItemsWithLooks() {
    Item item1 = itemRepository.findById(1L)
      .get();
    logger.info("Item 1: {}", item1);
    Item item2 = itemRepository.findById(2L)
      .get();
    Item item3 = itemRepository.findById(3L)
      .get();
    Item item4 = itemRepository.findById(4L)
      .get();
    Item item5 = itemRepository.findById(5L)
      .get();
    Item item6 = itemRepository.findById(6L)
      .get();
    Item item7 = itemRepository.findById(7L)
      .get();
    Item item8 = itemRepository.findById(8L)
      .get();
    Item item9 = itemRepository.findById(9L)
      .get();
    Item item10 = itemRepository.findById(10L)
      .get();

    // Add looks to items
    Look look1 = lookRepository.findById(1L)
      .get();
    Look look2 = lookRepository.findById(2L)
      .get();
    item1.getLooks()
      .add(look1);
    item2.getLooks()
      .add(look1);
    item10.getLooks()
      .add(look1);
    item4.getLooks()
      .add(look1);
    item5.getLooks()
      .add(look1);
    item6.getLooks()
      .add(look1);

    item3.getLooks()
      .add(look2);
    item8.getLooks()
      .add(look2);
    item7.getLooks()
      .add(look2);

    look1.getItems()
      .addAll(Arrays.asList(item1, item2, item10, item4, item5, item6));
    look2.getItems()
      .addAll(Arrays.asList(item3, item8, item7));

//     Add tags to items
    Tag tag1 = tagRepository.findById(1L)
      .get();
    Tag tag2 = tagRepository.findById(1L)
      .get();
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

    tag1.getItems()
      .addAll(Arrays.asList(item1, item2, item3));
    tag2.getItems()
      .addAll(Arrays.asList(item1, item2, item3));
    lookRepository.saveAll(Arrays.asList(look1, look2));
  }

  private Byte[] loadImage(String imageName) throws IOException {
    Resource resource =
      resourceLoader.getResource("classpath:static/images/" + imageName);
    byte[] byteArray = Files.readAllBytes(resource.getFile()
      .toPath());
    Byte[] wrapperArray = new Byte[byteArray.length];
    for (int i = 0; i < byteArray.length; i++) {
      wrapperArray[i] = byteArray[i]; // Autoboxing
    }
    return wrapperArray;
  }
}
