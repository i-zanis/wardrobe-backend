package io.noblackhole.wardrobe.wardrobebackend;

import io.noblackhole.wardrobe.wardrobebackend.domain.Look;

import java.util.List;
import java.util.Set;

public class TestLook {
  static Look create1() {
    return new Look.Builder()
      .withId(1111L)
      .withName("Casual")
      .withDescription("Morning time wear")
      .build();
  }


  static Look create2() {
    return new Look.Builder()
      .withId(1112L)
      .withName("formal")
      .withDescription("Evening time wear")
      .build();
  }

  Set<Look> createSet() {
    return Set.of(create1(), create2());
  }

  public static List<Look> createList() {
    return List.of(create1(), create2());
  }
}
