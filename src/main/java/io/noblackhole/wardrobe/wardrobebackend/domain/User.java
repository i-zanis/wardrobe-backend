package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Person {
  @Column(name = "phone")
  private String phone;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private List<Accessory> accessories = new ArrayList<>();
}
