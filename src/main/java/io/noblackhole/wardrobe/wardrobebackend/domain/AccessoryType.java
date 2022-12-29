package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.Column;

public class AccessoryType extends BaseEntity {
  @Column(name = "name")
  private String name;

  public static final class AccessoryTypeBuilder {
    private String name;

    private AccessoryTypeBuilder() {
    }

    public static AccessoryTypeBuilder anAccessoryType() {
      return new AccessoryTypeBuilder();
    }

    public AccessoryTypeBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public AccessoryTypeBuilder withId(Long id) {
      return this;
    }

    public AccessoryType build() {
      AccessoryType accessoryType = new AccessoryType();
      accessoryType.name = this.name;
      return accessoryType;
    }
  }
}
