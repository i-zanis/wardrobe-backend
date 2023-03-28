package io.noblackhole.wardrobe.wardrobebackend;

import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;

import java.util.List;
import java.util.Set;

public class TestTag {
  public static Tag create1() {
    return new Tag.Builder()
      .withId(11111L)
      .withName("Gucci")
      .build();
  }

  public static Tag create2() {
    return new Tag.Builder()
      .withId(11112L)
      .withName("Trousers")
      .build();
  }

  public static List<Tag> createList() {
    return List.of(create1(), create2());
  }

  public static Set<Tag> createSet() {
    return Set.of(create1(), create2());
  }
}
