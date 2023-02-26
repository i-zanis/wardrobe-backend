package io.noblackhole.wardrobe.wardrobebackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag extends BaseEntity {
  String name;
  @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
  @Fetch(FetchMode.JOIN)
  @JsonBackReference
  @JsonIgnore
  Set<Item> items = new HashSet<>();

  public Tag(String name) {
    this.name = name;
  }

  public Tag(Long id, String name) {
    super(id);
    this.name = name;
  }

  public Tag(String name, Set<Item> items) {
    this.name = name;
    this.items = items;
  }

  public Tag(Long id, String name, Set<Item> items) {
    super(id);
    this.name = name;
    this.items = items;
  }

  public Tag() {

  }

  public Set<Item> getItems() {
    return items;
  }

  public void setItems(Set<Item> items) {
    if (items == null)
      items = new HashSet<>();
    this.items = items;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
