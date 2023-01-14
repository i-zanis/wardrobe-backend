package io.noblackhole.wardrobe.wardrobebackend.controller;

public class ItemDeleteDTO {
  Long id;
  Long userId;

  public Long getId() {
    return id;
  }

  public Long getUserId() {
    return userId;
  }

  public ItemDeleteDTO(Long id, Long userId) {
    this.id = id;
    this.userId = userId;
  }
}
