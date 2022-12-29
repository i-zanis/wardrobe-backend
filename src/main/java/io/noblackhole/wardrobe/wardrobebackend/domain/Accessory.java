package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Accessory extends BaseEntity {
  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "type_id")
  private AccessoryType accessoryType;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Column(name = "add_date")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate addDate;

}
