package io.noblackhole.wardrobe.wardrobebackend.domain.dto.item;

import io.noblackhole.wardrobe.wardrobebackend.domain.Category;
import io.noblackhole.wardrobe.wardrobebackend.domain.Look;
import io.noblackhole.wardrobe.wardrobebackend.domain.Tag;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

public record ItemDto(Long id, String name, Instant createdAt,
                      String brand, String size, Set<String> colors,
                      List<Tag> tags,
                      Category category,
                      List<Look> looks,
                      Boolean isFavorite, Long userId, Double price,
                      String imageLocalPath,
                      // TODO(jtl): to remove imagData
                      Byte[] imageData, String notes) implements Serializable {
  public static final class Builder {
    private Long id;
    private String name;
    private Instant createdAt;
    private String brand;
    private String size;
    private Set<String> colors;
    private List<Tag> tags;
    private Category category;
    private List<Look> looks;
    private Boolean isFavorite;
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

    public Builder withCreatedAt(Instant createdAt) {
      this.createdAt = createdAt;
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

    public Builder withColors(Set<String> colors) {
      this.colors = colors;
      return this;
    }

    public Builder withTags(List<Tag> tags) {
      this.tags = tags;
      return this;
    }

    public Builder withCategory(Category category) {
      this.category = category;
      return this;
    }

    public Builder withLooks(List<Look> looks) {
      this.looks = looks;
      return this;
    }

    public Builder withIsFavorite(Boolean isFavorite) {
      this.isFavorite = isFavorite;
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

    public ItemDto build() {
      return new ItemDto(id, name, createdAt, brand, size, colors, tags,
        category, looks, isFavorite, userId, price, imageLocalPath, imageData
        , notes);
    }
  }
}
