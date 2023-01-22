package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

public enum Category {
  TOP, BOTTOM, SHOES, ACCESSORIES, INNER_WEAR, OTHER
}
