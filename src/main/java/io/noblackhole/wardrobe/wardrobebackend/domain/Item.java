package io.noblackhole.wardrobe.wardrobebackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {
  @Timestamp
  @Column(name = "created_at", nullable = false)
  private final LocalDateTime createdAt = LocalDateTime.now();
  String name;
  String brand;
  String size;
  @Enumerated
  private Set<Color> colors = new HashSet<>();
  @ManyToMany(mappedBy = "items")
  private Set<Tag> tags = new HashSet<>();
  @Enumerated(EnumType.STRING)
  private Category category;
  @ManyToMany(mappedBy = "items")
  private Set<Look> looks;
  private boolean isFavorite;
  private Double price;
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "user_id")
  private User user;
  private String imageLocalPath;
  @Lob
  private Byte[] imageData;
  private String notes;


  public Item(String name, Set<Color> colors, String brand, Category category, Set<Look> looks, boolean isFavorite, User user, Double price, String imageLocalPath, Byte[] imageData, Set<Tag> tags, String notes, String size) {
    this.name = name;
    this.colors = colors;
    this.brand = brand;
    this.category = category;
    this.looks = looks;
    this.isFavorite = isFavorite;
    this.user = user;
    this.price = price;
    this.imageLocalPath = imageLocalPath;
    this.imageData = imageData;
    this.tags = tags;
    this.notes = notes;
    this.size = size;
  }

  public Item(Long id, String name, Set<Color> colors, String brand, Category category, Set<Look> looks, boolean isFavorite, User user, Double price, String imageLocalPath, Byte[] imageData, Set<Tag> tags, String notes, String size) {
    super(id);
    this.name = name;
    this.colors = colors;
    this.brand = brand;
    this.category = category;
    this.looks = looks;
    this.isFavorite = isFavorite;
    this.user = user;
    this.price = price;
    this.imageLocalPath = imageLocalPath;
    this.imageData = imageData;
    this.tags = tags;
    this.notes = notes;
    this.size = size;
  }

  public Item() {

  }

  public Item(Set<Color> colors, String brand, Category category, Set<Look> looks, boolean isFavorite, User user, Double price, Byte[] imageData, String notes, String size) {
    this.colors = colors;
    this.brand = brand;
    this.category = category;
    this.looks = looks;
    this.isFavorite = isFavorite;
    this.user = user;
    this.price = price;
    this.imageData = imageData;
    this.notes = notes;
    this.size = size;
  }

  public Set<Tag> getTags() {
    if (tags == null) tags = new HashSet<>();
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    if (tags == null) {
      tags = new HashSet<>();
    }
    this.tags = tags;
  }

  public String getImageLocalPath() {
    return imageLocalPath;
  }

  public void setImageLocalPath(String imageLocalPath) {
    this.imageLocalPath = imageLocalPath;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Set<Look> getLooks() {
    if (looks == null) {
      looks = new HashSet<>();
    }
    return looks;
  }

  public void setLooks(Set<Look> looks) {
    this.looks = looks;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public Byte[] getImageData() {
    return imageData;
  }

  public void setImageData(Byte[] imageData) {
    this.imageData = imageData;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  public void setFavorite(boolean favorite) {
    isFavorite = favorite;
  }

  public void addColor(Color color) {
    colors.add(color);
  }

  public Set<Color> getColors() {
    if (colors == null) {
      colors = new HashSet<>();
    }
    return colors;
  }

  public void setColors(Set<Color> colors) {
    this.colors = colors;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Byte[] getImage() {
    return imageData;
  }

  public void setImage(Byte[] imageData) {
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
    private LocalDateTime createdAt;
    private String name;
    private Set<Color> colors;
    private String brand;
    private Category category;
    private Set<Look> looks;
    private boolean isFavorite;
    private User user;
    private Double price;
    private String imageLocalPath;
    private Byte[] imageData;
    private Set<Tag> tag;
    private String notes;
    private String size;

    public Builder() {
    }

    public static Builder anItem() {
      return new Builder();
    }

    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withCreatedAt(LocalDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withColors(Set<Color> colors) {
      this.colors = colors;
      return this;
    }

    public Builder withBrand(String brand) {
      this.brand = brand;
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

    public Builder withIsFavorite(boolean isFavorite) {
      this.isFavorite = isFavorite;
      return this;
    }

    public Builder withUser(User user) {
      this.user = user;
      return this;
    }

    public Builder withPrice(Double price) {
      this.price = price;
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

    public Builder withTag(Set<Tag> tag) {
      this.tag = tag;
      return this;
    }

    public Builder withNotes(String notes) {
      this.notes = notes;
      return this;
    }

    public Builder withSize(String size) {
      this.size = size;
      return this;
    }

    public Item build() {
      Item item = new Item(colors, brand, category, looks, isFavorite, user, price, imageData, notes, size);
      item.setId(id);
      item.setName(name);
      item.setImageLocalPath(imageLocalPath);
      item.setTags(tag);
      return item;
    }
  }
}
