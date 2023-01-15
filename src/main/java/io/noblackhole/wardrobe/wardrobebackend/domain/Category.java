package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

@Entity
public class Category extends BaseEntity {
  private String name;
  @OneToOne
  private Item item;

  public Category(String name, Item item) {
    this.name = name;
    this.item = item;
  }

  public Category(Long id, String name, Item item) {
    super(id);
    this.name = name;
    this.item = item;
  }

  public Category(Long id, String name) {
    super(id);
    this.name = name;
  }

  public Category() {

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }
}
