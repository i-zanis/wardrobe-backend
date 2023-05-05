package io.noblackhole.wardrobe.wardrobebackend.domain.dto.item;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;

import java.io.Serializable;
import java.util.List;

public record ItemCreationDto(Long id, String name, String brand, String size,
                              List<String> colors, List<Tag> tags,
                              Boolean isFavorite, Category category,
                              Long userId, Double price,
                              String imageLocalPath, Byte[] imageData,
                              String notes) implements Serializable {
  public static final class Builder {
    private Long id;
    private String name;
    private String brand;
    private String size;
    private List<String> colors;
    private List<Tag> tags;
    private Boolean isFavorite;
    private Category category;
    private Long userId;
    private Double price;
    private String imageLocalPath;
    private Byte[] imageData;
    private String notes;

    public Builder() {
    }
    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withBrand(String brand) {
      this.brand = brand;
      return this;
    }

    public Builder withSize(String size) {
      this.size = size;
      return this;
    }

    public Builder withColors(List<String> colors) {
      this.colors = colors;
      return this;
    }

    public Builder withTags(List<Tag> tags) {
      this.tags = tags;
      return this;
    }

    public Builder withIsFavorite(Boolean isFavorite) {
      this.isFavorite = isFavorite;
      return this;
    }

    public Builder withCategory(Category category) {
      this.category = category;
      return this;
    }

    public Builder withUserId(Long userId) {
      this.userId = userId;
      return this;
    }

    public Builder withPrice(Double price) {
      this.price = price;
      return this;
    }

    public Builder withImageLocalPath(String imageLocalPath) {
      this.imageLocalPath = imageLocalPath;
      return this;
    }

    public Builder withImageData(Byte[] imageData) {
      this.imageData = imageData;
      return this;
    }

    public Builder withNotes(String notes) {
      this.notes = notes;
      return this;
    }

    public ItemCreationDto build() {
      return new ItemCreationDto(id, name, brand, size, colors, tags,
        isFavorite, category, userId, price, imageLocalPath, imageData, notes);
    }
  }
}