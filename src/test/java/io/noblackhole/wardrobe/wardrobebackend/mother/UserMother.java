package io.noblackhole.wardrobe.wardrobebackend.mother;

import io.noblackhole.wardrobe.wardrobebackend.domain.User;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.PreferencesDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserCreationDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.user.UserDto;

public class UserMother {

  public static User createUser() {
    return new User.Builder().withId(1L)
      .withFirstName("John")
      .withLastName("Doe")
      .withEmail("johndoe@gmail.com")
      .withPassword("password")
      .build();
  }

  public static User createUser2() {
    return new User.Builder().withId(2L)
      .withFirstName("Jane")
      .withLastName("Doe")
      .withEmail("janedoe@gmail.com")
      .withPassword("password")
      .build();
  }

  public static UserCreationDto createCreationDto1() {
    return new UserCreationDto("John", "Doe", "johndoe@gmail.com", "password");
  }

  public static UserCreationDto createCreationDto2() {
    return new UserCreationDto("Jane", "Doe", "janedoe@gmail.com", "password");
  }

  public static UserDto createUserDto1() {
    return new UserDto(1L, "John", "Doe", "johndoe@gmail.com",
      new PreferencesDto(false, true, false));
  }

  public static UserDto createUserDto2() {
    return new UserDto(2L, "Jane", "Doe", "janedoe@gmail.com",
      new PreferencesDto(false, true, false));
  }
}
