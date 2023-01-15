package io.noblackhole.wardrobe.wardrobebackend.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Person {
  @OneToOne
  private Preferences preferences;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<Item> items = new ArrayList<>();

  public User() {

  }

  public User(String firstName, String lastName, String email, Preferences preferences, String password) {
    super(firstName, lastName, email);
    this.preferences = preferences;
    this.password = password;
  }

  public User(Preferences preferences, String password) {
    this.preferences = preferences;
    this.password = password;
  }

  public Preferences getPreferences() {
    return preferences;
  }

  public void setPreferences(Preferences preferences) {
    this.preferences = preferences;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Item> getItems() {
//     TODO check if it needs removing after tests pass
    if (items == null) {
      items = new ArrayList<>();
    }
    return items;
  }

  public void setItems(List<Item> items) {
    this.items = items;
  }

  public void addItem(Item item) {
    getItems().add(item);
  }


  public static final class Builder {
    private Long id;
    private @NotBlank String firstName;
    private String lastName;
    private @Email String email;
    private Preferences preferences;
    private String password;
    private List<Item> items;

    public Builder() {
    }

    public static Builder anUser() {
      return new Builder();
    }

    public Builder withId(Long id) {
      this.id = id;
      return this;
    }

    public Builder withFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder withLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder withEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder withPreferences(Preferences preferences) {
      this.preferences = preferences;
      return this;
    }

    public Builder withPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder withItems(List<Item> items) {
      this.items = items;
      return this;
    }

    public User build() {
      User user = new User();
      user.setId(id);
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setEmail(email);
      user.setPreferences(preferences);
      user.setPassword(password);
      user.setItems(items);
      return user;
    }
  }
}
