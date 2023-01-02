package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "items")
public class Item extends BaseEntity {
  private Set<Color> colors = new HashSet<>();
  private String name;
  private String brand;
  @OneToOne
  private Category category;
  @ManyToOne
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
}
