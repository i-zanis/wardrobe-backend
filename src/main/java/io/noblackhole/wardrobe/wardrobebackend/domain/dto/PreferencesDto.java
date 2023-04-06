package io.noblackhole.wardrobe.wardrobebackend.domain.dto;


import java.io.Serializable;

public record PreferencesDto(
  Boolean isDarkMode,
  Boolean isMetric,
  Boolean isNotificationsEnabled
) implements Serializable {
}