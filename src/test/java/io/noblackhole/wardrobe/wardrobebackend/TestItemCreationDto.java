package io.noblackhole.wardrobe.wardrobebackend;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemCreationDto;

import java.util.List;

public class TestItemCreationDto {

  public static ItemCreationDto create1() {
    return create1(null);
  }

  public static ItemCreationDto create1(Long userId) {
    if (userId == null) {
      userId = TestUser.createUser1()
        .getId();
    }

    return new ItemCreationDto.Builder().withId(111L)
      .withName("Gucci Pants")
      .withBrand("Gucci")
      .withSize("M")
      .withColors(List.of("BLUE", "RED"))
      .withTags(TestTag.createList())
      .withIsFavorite(false)
      .withCategory(Category.TOP)
      .withLooks(TestLook.createList())
      .withUserId(userId)
      .withPrice(100.0)
      .withImageLocalPath("C:\\Users\\user\\Pictures\\image1.jpg")
      .withImageData(new Byte[]{1, 2, 3})
      .withNotes("These are the notes1")
      .build();

  }

  public static ItemCreationDto create2() {
    return create2(null);
  }

  public static ItemCreationDto create2(Long userId) {
    if (userId == null) {
      userId = TestUser.createUser2()
        .getId();
    }

    return new ItemCreationDto.Builder().withId(112L)
      .withName("Sun T-shirt")
      .withBrand("Sun MicroSystems")
      .withSize("XL")
      .withColors(List.of("GREEN", "ORANGE"))
      .withTags(List.of(new Tag("Geek"), new Tag("Tee")))
      .withIsFavorite(false)
      .withCategory(Category.TOP)
      .withLooks(TestLook.createList())
      .withUserId(userId)
      .withPrice(10.0)
      .withImageLocalPath("C:\\Users\\user\\Pictures\\image2.jpg")
      .withImageData(new Byte[]{4, 5, 6})
      .withNotes("These are the notes2")
      .build();
  }
}
