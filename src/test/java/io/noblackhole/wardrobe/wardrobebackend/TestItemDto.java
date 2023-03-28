package io.noblackhole.wardrobe.wardrobebackend;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;

import java.util.List;
import java.util.Set;

public class TestItemDto {

  public static ItemDto create1() {
    return create1(null);
  }

  public static ItemDto create1(Long userId) {
    if (userId == null) {
      userId = TestUser.createUser1()
        .getId();
    }

    return new ItemDto.Builder().withId(111L)
      .withName("Gucci Pants")
      .withBrand("Gucci")
      .withSize("M")
      .withColors(Set.of("BLUE", "RED"))
      .withTags(TestTag.createList())
      .withCategory(Category.TOP)
      .withLooks(TestLook.createList())
      .withUserId(userId)
      .withPrice(100.0)
      .withImageLocalPath("C:\\Users\\user\\Pictures\\image1.jpg")
      .withImageData(new Byte[]{1, 2, 3})
      .withNotes("These are the notes1")
      .build();

  }

  public static ItemDto create2() {
    return create2(null);
  }

  public static ItemDto create2(Long userId) {
    if (userId == null) {
      userId = TestUser.createUser2()
        .getId();
    }

    return new ItemDto.Builder().withId(112L)
      .withName("Sun T-shirt")
      .withBrand("Sun MicroSystems")
      .withSize("XL")
      .withColors(Set.of("GREEN", "ORANGE"))
      .withTags(List.of(new Tag("Geek"), new Tag("Tee")))
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
