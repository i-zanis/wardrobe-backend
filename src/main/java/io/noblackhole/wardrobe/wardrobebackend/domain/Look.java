package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "looks")
public class Look extends BaseEntity {
  @ManyToMany
  Set<Item> items;
  private String name;
  private String description;

  public Look(Set<Item> items, String name, String description) {
    this.items = items;
    this.name = name;
    this.description = description;
  }

  public Look(Long id, Set<Item> items, String name, String description) {
    super(id);
    this.items = items;
    this.name = name;
    this.description = description;
  }

  public Look() {

  }
}
