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
  @Enumerated
  private Set<Color> colors = new HashSet<>();
  private String brand;
  @Enumerated(EnumType.STRING)
  private Category category;
  @ManyToMany(mappedBy = "items")
  private Set<Look> looks;
  private boolean isFavorite;
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "user_id")
  private User user;
  private Double price;
  @Lob
  private Byte[] image;
  private String notes;
  private String size;

  public Item(Set<Color> colors, String brand, Category category, Set<Look> looks, boolean isFavorite, User user, Double price, Byte[] image, String notes, String size) {
    this.colors = colors;
    this.brand = brand;
    this.category = category;
    this.looks = looks;
    this.isFavorite = isFavorite;
    this.user = user;
    this.price = price;
    this.image = image;
    this.notes = notes;
    this.size = size;
  }

  public Item(Long id, Set<Color> colors, String brand, Category category, Set<Look> looks, boolean isFavorite, User user, Double price, Byte[] image, String notes, String size) {
    super(id);
    this.colors = colors;
    this.brand = brand;
    this.category = category;
    this.looks = looks;
    this.isFavorite = isFavorite;
    this.user = user;
    this.price = price;
    this.image = image;
    this.notes = notes;
    this.size = size;
  }

  public Item() {

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
    return image;
  }

  public void setImage(Byte[] image) {
    this.image = image;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public static final class Builder {
    private @Positive Long id;
    private Set<Color> colors;
    private String brand;
    private Category category;
    private Set<Look> looks;
    private boolean isFavorite;
    private User user;
    private Double price;
    private Byte[] image;
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

    public Builder withImage(Byte[] image) {
      this.image = image;
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
      Item item = new Item(colors, brand, category, looks, isFavorite, user, price, image, notes, size);
      item.setId(id);
      return item;
    }
  }
}
