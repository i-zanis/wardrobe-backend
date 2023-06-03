package io.noblackhole.wardrobe.wardrobebackend.mother;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemCreationDto;

import java.util.List;

public class ItemCreationDtoMother {

  public static ItemCreationDto create() {
    return create(null);
  }

  public static ItemCreationDto create(Long userId) {
    if (userId == null) {
      userId = UserMother.createUser()
        .getId();
    }

    return new ItemCreationDto.Builder().withId(111L)
      .withName("Gucci Pants")
      .withBrand("Gucci")
      .withSize("M")
      .withColors(List.of("BLUE", "RED"))
      .withTags(TagMother.createList())
      .withIsFavorite(false)
      .withCategory(Category.TOP)
//      .withLooks(TestLookIdDto.createList())
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
      userId = UserMother.createUser2()
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
//      .withLooks(TestLookIdDto.createList())
      .withUserId(userId)
      .withPrice(10.0)
      .withImageLocalPath("C:\\Users\\user\\Pictures\\image2.jpg")
      .withImageData(new Byte[]{4, 5, 6})
      .withNotes("These are the notes2")
      .build();
  }
}
