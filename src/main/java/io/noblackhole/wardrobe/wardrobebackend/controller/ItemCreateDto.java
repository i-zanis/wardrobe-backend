package io.noblackhole.wardrobe.wardrobebackend.controller;

public class ItemCreateDto {
  String name;
  String description;
  String imageUrl;
  Long userId;

  public ItemCreateDto(String name, String description, String imageUrl, Long userId) {
    this.name = name;
    this.description = description;
    this.imageUrl = imageUrl;
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public Long getUserId() {
    return userId;
  }
}
