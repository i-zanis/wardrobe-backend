package io.noblackhole.wardrobe.wardrobebackend.mother;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Item;
import io.noblackhole.wardrobe.wardrobebackend.domain.User;

import java.util.Set;

public class ItemMother extends Item {

  public static Item create() {
    return create(null);
  }

  public static Item create(User user) {
    if (user == null) {
      user = UserMother.createUser();
    }

    Category category1 = Category.SHOES;
    return new Builder().withId(11L)
      .withBrand("Nike")
      .withCategory(category1)
      .withColors(Set.of("BLUE", "RED"))
      .withUser(user)
      .withPrice(10.59)
      .withImageData(new Byte[]{1, 2, 3})
      .withNotes("These are the notes1")
      .build();
  }

  public static Item create2() {
    return create2(null);
  }

  public static Item create2(User user) {
    if (user == null) {
      user = UserMother.createUser2();
    }
    Category category = Category.BOTTOM;
    return new Item.Builder().withId(12L)
      .withBrand("Mystic's Fate")
      .withCategory(category)
      .withColors(Set.of("PINK", "WHITE"))
      .withUser(user)
      .withPrice(200.0)
      .withImageData(new Byte[]{2, 3, 4})
      .withNotes("These are the notes2")
      .build();
  }
}
