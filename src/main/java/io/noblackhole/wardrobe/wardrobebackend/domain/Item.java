package io.noblackhole.wardrobe.wardrobebackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {
  @Enumerated
  private Set<Color> colors = new HashSet<>();
  private String brand;
  @Enumerated(EnumType.STRING)
  private Category category;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "look_id")
  private Look look;
  private boolean isFavorite;
  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  @JoinColumn(name = "user_id")
  private User user;
  private Double price;
  @Lob
  private Byte[] image;
  private String material;
  // Offer a few options but leave as a String to allow the user to select
  // his own location
  private String location;
  private String care;
  private String notes;

  public Item(Set<Color> colors, String brand, Category category, Look look, boolean isFavorite, User user, Double price, Byte[] image, String material, String location, String care, String notes) {
    this.colors = colors;
    this.brand = brand;
    this.category = category;
    this.look = look;
    this.isFavorite = isFavorite;
    this.user = user;
    this.price = price;
    this.image = image;
    this.material = material;
    this.location = location;
    this.care = care;
    this.notes = notes;
  }

  public Item(Long id, Set<Color> colors, String brand, Category category, Look look, boolean isFavorite, User user, Double price, Byte[] image, String material, String location, String care, String notes) {
    super(id);
    this.colors = colors;
    this.brand = brand;
    this.category = category;
    this.look = look;
    this.isFavorite = isFavorite;
    this.user = user;
    this.price = price;
    this.image = image;
    this.material = material;
    this.location = location;
    this.care = care;
    this.notes = notes;
  }

  public Item() {
  }

  public Look getLook() {
    return look;
  }

  public void setLook(Look look) {
    this.look = look;
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

  public String getMaterial() {
    return material;
  }

  public void setMaterial(String material) {
    this.material = material;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getCare() {
    return care;
  }

  public void setCare(String care) {
    this.care = care;
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
    private Look look;
    private boolean isFavorite;
    private User user;
    private Double price;
    private Byte[] image;
    private String material;
    private String location;
    private String care;
    private String notes;

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

    public Builder withLook(Look look) {
      this.look = look;
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

    public Builder withMaterial(String material) {
      this.material = material;
      return this;
    }

    public Builder withLocation(String location) {
      this.location = location;
      return this;
    }

    public Builder withCare(String care) {
      this.care = care;
      return this;
    }

    public Builder withNotes(String notes) {
      this.notes = notes;
      return this;
    }

    public Item build() {
      Item item = new Item(colors, brand, category, look, isFavorite, user, price, image, material, location, care, notes);
      item.setId(id);
      return item;
    }
  }
}
