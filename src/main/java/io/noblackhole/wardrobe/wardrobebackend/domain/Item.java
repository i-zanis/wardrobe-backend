package io.noblackhole.wardrobe.wardrobebackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
  @OneToOne
  private Category category;
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

  public Item() {
  }


  public void addColor(Color color) {
    colors.add(color);
  }


  @Override
  public String toString() {
    return new StringJoiner(", ", Item.class.getSimpleName() + "[", "]")
      .add("colors=" + colors)
      .add("brand='" + brand + "'")
      .add("category=" + category)
      .add("user=" + user)
      .add("price=" + price)
      .add("image=" + Arrays.toString(image))
      .add("material='" + material + "'")
      .add("location='" + location + "'")
      .add("care='" + care + "'")
      .add("notes='" + notes + "'")
      .toString();
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
    private User user;
    private Double price;
    private Byte[] image;
    private String material;
    private String location;
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


    public Builder withNotes(String notes) {
      this.notes = notes;
      return this;
    }

    public Item build() {
      Item item = new Item();
      item.setId(id);
      item.setColors(colors);
      item.setBrand(brand);
      item.setCategory(category);
      item.setUser(user);
      item.setPrice(price);
      item.setImage(image);
      item.setMaterial(material);
      item.setLocation(location);
      item.setNotes(notes);
      return item;
    }
  }
}
