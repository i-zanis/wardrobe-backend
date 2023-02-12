package io.noblackhole.wardrobe.wardrobebackend.domain.dto.item;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Color;
import io.noblackhole.wardrobe.wardrobebackend.domain.Look;
import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;

import java.util.Set;

public record ItemCreationDto(
  Long id,
  String name,
  String brand,
  String size,
  Set<Color> colors,
  Set<Tag> tags,
  Category category,
  Set<Look> looks,
  boolean isFavorite,
  Long userId,
  Double price,
  String imageLocalPath,
  Byte[] imageData,
  String notes
) {
}