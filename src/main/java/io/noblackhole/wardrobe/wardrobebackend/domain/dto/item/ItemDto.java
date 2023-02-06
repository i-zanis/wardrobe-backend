package io.noblackhole.wardrobe.wardrobebackend.domain.dto.item;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Color;
import io.noblackhole.wardrobe.wardrobebackend.domain.Look;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

public record ItemDto(Long id, LocalDateTime createdAt, Set<Color> colors,
                      String brand, Category category, Set<Look> looks,
                      boolean isFavorite, Long userId, Double price,
                      String notes, String size) implements Serializable {
}

