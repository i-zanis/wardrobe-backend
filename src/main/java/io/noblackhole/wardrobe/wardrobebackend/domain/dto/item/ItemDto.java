package io.noblackhole.wardrobe.wardrobebackend.domain.dto.item;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Color;
import io.noblackhole.wardrobe.wardrobebackend.domain.Look;
import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

public record ItemDto(Long id, String name, LocalDateTime createdAt,
                      String brand, String size, Set<Color> colors,
                      Set<Tag> tags, Category category, Set<Look> looks,
                      boolean isFavorite, Long userId, Double price,
                      String imageLocalPath,
                      // TODO(jtl): to remove imagData
                      Byte[] imageData, String notes) implements Serializable {
}