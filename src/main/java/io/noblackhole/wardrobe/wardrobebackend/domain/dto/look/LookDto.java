package io.noblackhole.wardrobe.wardrobebackend.domain.dto.look;

import io.noblackhole.wardrobe.wardrobebackend.domain.Item;

import java.io.Serializable;
import java.util.List;

public record LookDto(Long id,
                      List<Item> items,
                      String name,
                      String description, Byte[] lookImageData,
                      Byte[] lookWithUserImageData,
                      Long userId) implements Serializable {

  public static final class Builder {
    private Long id;
    private List<Item> items;
    private String name;
    private String description;
    private Byte[] lookImageData;
    private Byte[] lookWithUserImageData;
    private Long userId;

    public Builder() {
    }

    public static Builder aLookDto() {
      return new Builder();
    }

    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withItems(List<Item> items) {
      this.items = items;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder withLookImageData(Byte[] lookImageData) {
      this.lookImageData = lookImageData;
      return this;
    }

    public Builder withLookWithUserImageData(Byte[] lookWithUserImageData) {
      this.lookWithUserImageData = lookWithUserImageData;
      return this;
    }

    public Builder withUserId(Long userId) {
      this.userId = userId;
      return this;
    }

    public LookDto build() {
      return new LookDto(id, items, name, description, lookImageData,
        lookWithUserImageData, userId);
    }
  }
}
