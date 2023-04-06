package io.noblackhole.wardrobe.wardrobebackend.domain.dto.user;

import io.noblackhole.wardrobe.wardrobebackend.domain.dto.PreferencesDto;
import io.noblackhole.wardrobe.wardrobebackend.domain.dto.item.ItemDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * This class is used to send data on app startup.
 */
public record UserItemsDto(
  @NotBlank(message = "Id is required") Long id,
  @NotBlank(message = "First name is required") String firstName,
  @NotBlank(message = "Last name is required") String lastName,
  @NotBlank(message = "Email is required") @Email(message = "Email must be valid")
  String email,
  PreferencesDto preferences,
  List<ItemDto> items
) implements UserDtoBase {
}