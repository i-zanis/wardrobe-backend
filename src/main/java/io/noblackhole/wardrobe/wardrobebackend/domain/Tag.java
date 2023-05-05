package io.noblackhole.wardrobe.wardrobebackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag extends BaseEntity {
  @Column(unique = true)
  String name;

  @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER, cascade =
    CascadeType.ALL)
  @Fetch(FetchMode.JOIN)
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
    if (items == null) items = new HashSet<>();
    this.items = items;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public static final class Builder {
    private @Positive Long id;
    private String name;
    private Set<Item> items;

    public Builder() {}


    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withItems(Set<Item> items) {
      this.items = items;
      return this;
    }

    public Tag build() {
      Tag tag = new Tag();
      tag.setId(id);
      tag.setName(name);
      tag.setItems(items);
      return tag;
    }
  }
}
