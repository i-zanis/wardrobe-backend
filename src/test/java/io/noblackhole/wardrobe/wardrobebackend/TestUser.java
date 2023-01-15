package io.noblackhole.wardrobe.wardrobebackend;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;

public class TestUser extends User {

  public static User create1() {
    return new User.Builder().withId(1L)
      .withFirstName("John")
      .withLastName("Doe")
      .withEmail("johndoe@gmail.com")
      .withPassword("password")
      .build();
  }

  public static User create2() {
    return new User.Builder().withId(2L)
      .withFirstName("Jane")
      .withLastName("Doe")
      .withEmail("janedoe@gmail.com")
      .withPassword("password")
      .build();
  }
}
