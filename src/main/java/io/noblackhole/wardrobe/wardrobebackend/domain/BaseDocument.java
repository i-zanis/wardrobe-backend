package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.Column;

public class BaseDocument extends BaseEntity {
  @Column(name = "header")
  String header;
  @Column(name = "body")
  String body;
}
