package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Set;

@Entity
public class Look extends BaseEntity {
  @OneToMany
  Set<Item> items;
  private String name;
  private String description;

}
