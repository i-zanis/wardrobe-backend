package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public BaseEntity() {
  }

  public Long getId() {
    return id;
  }

  public boolean isNew() {
    return this.id == null;
  }
}
