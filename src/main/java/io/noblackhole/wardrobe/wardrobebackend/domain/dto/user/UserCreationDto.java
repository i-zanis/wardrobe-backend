package io.noblackhole.wardrobe.wardrobebackend.domain.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreationDto(
  @NotBlank(message = "First name is required") String firstName,
  @NotBlank(message = "Last name is required") String lastName,
  @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email,
  @NotBlank(message = "Password is required") String password) implements UserDtoBase {
}