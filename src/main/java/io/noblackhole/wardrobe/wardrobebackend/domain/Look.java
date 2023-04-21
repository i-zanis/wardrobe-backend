package io.noblackhole.wardrobe.wardrobebackend.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "looks")
public class Look extends BaseEntity {
  @ManyToMany(mappedBy = "looks", fetch = FetchType.EAGER, cascade =
    CascadeType.ALL)
  @Fetch(FetchMode.JOIN)
  @JsonManagedReference
  private Set<Item> items = new HashSet<>();
  private String name;
  private String description;
  @Lob
  private Byte[] lookImageData;
  @Lob
  private Byte[] lookWithUserImageData;

  public Look(Set<Item> items, String name, String description) {
    this.items = items;
    this.name = name;
    this.description = description;
  }

  public Look(Long id, Set<Item> items, String name, String description) {
    super(id);
    this.items = items;
    this.name = name;
    this.description = description;
  }

  public Look(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Look(Long id, String name, String description) {
    super(id);
    this.name = name;
    this.description = description;
  }


  public Look() {
  }

  public Byte[] getLookImageData() {
    return lookImageData;
  }

  public void setLookImageData(Byte[] lookImageData) {
    this.lookImageData = lookImageData;
  }

  public Byte[] getLookWithUserImageData() {
    return lookWithUserImageData;
  }

  public void setLookWithUserImageData(Byte[] lookWithUserImageData) {
    this.lookWithUserImageData = lookWithUserImageData;
  }

  public Set<Item> getItems() {
    if (items == null)
      return new HashSet<>();
    return items;
  }

  public void setItems(Set<Item> items) {
    this.items = items;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static final class Builder {
    private @Positive Long id;
    private Set<Item> items;
    private String name;
    private String description;
    private Byte[] lookImageData;
    private Byte[] lookWithUserImageData;

    public Builder() {
    }

    public static Builder aLook() {
      return new Builder();
    }

    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withItems(Set<Item> items) {
      this.items = items;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder withDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder withLookImageData(Byte[] lookImageData) {
      this.lookImageData = lookImageData;
      return this;
    }

    public Builder withLookWithUserImageData(Byte[] lookWithUserImageData) {
      this.lookWithUserImageData = lookWithUserImageData;
      return this;
    }

    public Look build() {
      Look look = new Look();
      look.setId(id);
      look.setItems(items);
      look.setName(name);
      look.setDescription(description);
      look.setLookImageData(lookImageData);
      look.setLookWithUserImageData(lookWithUserImageData);
      return look;
    }
  }
}
