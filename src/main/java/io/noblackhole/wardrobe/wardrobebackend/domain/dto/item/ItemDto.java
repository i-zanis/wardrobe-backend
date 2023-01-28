package io.noblackhole.wardrobe.wardrobebackend.domain.dto.item;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Color;

import java.io.Serializable;
import java.util.Set;

public record ItemDto(
    Long id,
    Set<Color> colors,
    String brand, 
    Category category,
    boolean isFavorite, 
    Double price,
    Byte[] image, 
    String notes, 
    String size
) implements Serializable { }