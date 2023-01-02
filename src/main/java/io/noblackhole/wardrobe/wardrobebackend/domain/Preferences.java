package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Preferences extends BaseEntity {
  String location;
  @Column(name = "wear_type")
  @Enumerated(value = EnumType.STRING)
  WearType wearType;
  Scale scale;
}
