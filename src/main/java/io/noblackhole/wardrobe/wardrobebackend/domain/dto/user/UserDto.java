package io.noblackhole.wardrobe.wardrobebackend.domain.dto.user;

import io.noblackhole.wardrobe.wardrobebackend.domain.dto.PreferencesDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDto(@NotBlank(message = "Id is required") Long id,
                      @NotBlank(message = "First name is required") String firstName,
                      @NotBlank(message = "Last name is required") String lastName,
                      @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email,
                      PreferencesDto preferences) implements UserDtoBase {
}
