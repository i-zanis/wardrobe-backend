package io.noblackhole.wardrobe.wardrobebackend;

import io.noblackhole.wardrobe.wardrobebackend.domain.*;

import java.util.Set;

public class TestItem extends Item {

  public static Item create1() {
    return create1(null);
  }

  public static Item create1(User user) {
    if (user == null) {
      user = TestUser.create1();
    }
    Category category1 = new Category(1L, "Footwear");
    return new Builder()
      .withId(11L)
      .withBrand("Hallmark Devilson")
      .withCategory(category1)
      .withColors(Set.of(Color.BLUE, Color.RED))
      .withUser(user)
      .withPrice(100.0)
      .withImage(new Byte[]{1, 2, 3})
      .withMaterial(Material.LACE.getMaterial())
      .withLocation("New York")
      .withNotes("These are the notes")
      .build();
  }

  public static Item create2() {
    return create2(null);
  }

  public static Item create2(User user) {
    if (user == null) {
      user = TestUser.create2();
    }
    Category category = new Category(1L, "Dress");
    return new Item.Builder().withId(12L)
      .withBrand("Mystic's Fate")
      .withCategory(category)
      .withColors(Set.of(Color.PINK, Color.WHITE))
      .withUser(user)
      .withPrice(200.0)
      .withImage(new Byte[]{2, 3, 4})
      .withMaterial(Material.COTTON.getMaterial())
      .withLocation("Vladivostok")
      .withNotes("These are the notes")
      .build();
  }
}
