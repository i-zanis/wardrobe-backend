package io.noblackhole.wardrobe.wardrobebackend.mother;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;

import java.util.List;
import java.util.Set;

public class ItemDtoMother {

  public static ItemDto create() {
    return create(null);
  }

  public static ItemDto create(Long userId) {
    if (userId == null) {
      userId = UserMother.createUser()
        .getId();
    }

    return new ItemDto.Builder().withId(111L)
      .withName("Gucci Pants")
      .withBrand("Gucci")
      .withSize("M")
      .withColors(Set.of("BLUE", "RED"))
      .withTags(TagMother.createList())
      .withCategory(Category.TOP)
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
      userId = UserMother.createUser2()
        .getId();
    }

    return new ItemDto.Builder().withId(112L)
      .withName("Sun T-shirt")
      .withBrand("Sun MicroSystems")
      .withSize("XL")
      .withColors(Set.of("GREEN", "ORANGE"))
      .withTags(List.of(new Tag("Geek"), new Tag("Tee")))
      .withCategory(Category.TOP)
      .withUserId(userId)
      .withPrice(10.0)
      .withImageLocalPath("C:\\Users\\user\\Pictures\\image2.jpg")
      .withImageData(new Byte[]{4, 5, 6})
      .withNotes("These are the notes2")
      .build();
  }
}
