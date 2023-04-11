package io.noblackhole.wardrobe.wardrobebackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import jdk.jfr.Timestamp;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {
  @Timestamp
  @Column(name = "created_at", nullable = false)
  private final Instant createdAt = Instant.now();
  String name;
  String brand;
  String size;
  @ElementCollection
  private Set<String> colors = new HashSet<>();
  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//  @JoinTable(name = "item_tag", joinColumns = @JoinColumn(name = "item_id")
//  , inverseJoinColumns = @JoinColumn(name = "tag_id"))
  @JsonManagedReference
  private Set<Tag> tags = new HashSet<>();

  private Boolean isFavorite;
  @Enumerated(EnumType.STRING)
  private Category category;

  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
//  @JoinTable(name = "items_looks", joinColumns = @JoinColumn(name =
//  "item_id"), inverseJoinColumns = @JoinColumn(name = "look_id"))
  @JsonManagedReference
  private Set<Look> looks = new HashSet<>();
  private Double price;
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "user_id")
  private User user;
  private String imageLocalPath;
  @Lob
  private Byte[] imageData;
  private String notes;

  public Item(String name, String brand, String size, Set<String> colors,
              Set<Tag> tags, Boolean isFavorite, Category category,
              Set<Look> looks, Double price, User user, String imageLocalPath
    , Byte[] imageData, String notes) {
    this.name = name;
    this.brand = brand;
    this.size = size;
    this.colors = colors;
    this.tags = tags;
    this.isFavorite = isFavorite;
    this.category = category;
    this.looks = looks;
    this.price = price;
    this.user = user;
    this.imageLocalPath = imageLocalPath;
    this.imageData = imageData;
    this.notes = notes;
  }

  public Item(Long id, String name, String brand, String size,
              Set<String> colors, Set<Tag> tags, Boolean isFavorite,
              Category category, Set<Look> looks, Double price, User user,
              String imageLocalPath, Byte[] imageData, String notes) {
    super(id);
    this.name = name;
    this.brand = brand;
    this.size = size;
    this.colors = colors;
    this.tags = tags;
    this.isFavorite = isFavorite;
    this.category = category;
    this.looks = looks;
    this.price = price;
    this.user = user;
    this.imageLocalPath = imageLocalPath;
    this.imageData = imageData;
    this.notes = notes;
  }

  public Item() {

  }

  public Boolean getIsFavorite() {
    return isFavorite;
  }

  public void setIsFavorite(Boolean favorite) {
    isFavorite = favorite;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public Set<String> getColors() {
    return colors;
  }

  public void setColors(Set<String> colors) {
    this.colors = colors;
  }

  public Set<Tag> getTags() {
    if (tags == null) return Collections.emptySet();
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  public Boolean isFavorite() {
    return isFavorite;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Set<Look> getLooks() {
    return looks;
  }

  public void setLooks(Set<Look> looks) {
    this.looks = looks;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getImageLocalPath() {
    return imageLocalPath;
  }

  public void setImageLocalPath(String imageLocalPath) {
    this.imageLocalPath = imageLocalPath;
  }

  public Byte[] getImageData() {
    return imageData;
  }

  public void setImageData(Byte[] imageData) {
    this.imageData = imageData;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public static final class Builder {
    private @Positive Long id;
    private Instant createdAt;
    private String name;
    private String brand;
    private String size;
    private Set<String> colors;
    private Set<Tag> tags;
    private Boolean isFavorite;
    private Category category;
    private Set<Look> looks;
    private Double price;
    private User user;
    private String imageLocalPath;
    private Byte[] imageData;
    private String notes;

    public Builder() {
    }

    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withBrand(String brand) {
      this.brand = brand;
      return this;
    }

    public Builder withSize(String size) {
      this.size = size;
      return this;
    }

    public Builder withColors(Set<String> colors) {
      this.colors = colors;
      return this;
    }

    public Builder withTags(Set<Tag> tags) {
      this.tags = tags;
      return this;
    }

    public Builder withIsFavorite(Boolean isFavorite) {
      this.isFavorite = isFavorite;
      return this;
    }

    public Builder withCategory(Category category) {
      this.category = category;
      return this;
    }

    public Builder withLooks(Set<Look> looks) {
      this.looks = looks;
      return this;
    }

    public Builder withPrice(Double price) {
      this.price = price;
      return this;
    }

    public Builder withUser(User user) {
      this.user = user;
      return this;
    }

    public Builder withImageLocalPath(String imageLocalPath) {
      this.imageLocalPath = imageLocalPath;
      return this;
    }

    public Builder withImageData(Byte[] imageData) {
      this.imageData = imageData;
      return this;
    }

    public Builder withNotes(String notes) {
      this.notes = notes;
      return this;
    }

    public Item build() {
      Item item = new Item(name, brand, size, colors, tags, isFavorite,
        category, looks, price, user, imageLocalPath, imageData, notes);
      item.setId(id);
      return item;
    }
  }
}