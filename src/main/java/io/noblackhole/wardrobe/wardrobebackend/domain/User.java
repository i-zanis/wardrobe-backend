package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Person {
  @OneToOne
  private Preferences preferences;
  private String password;
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
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
    return items;
  }

  public void addItem(Item item) {
    getItems().add(item);
  }

  public static final class UserBuilder {
    private String firstName;
    private String lastName;
    private String email;
    private Preferences preferences;
    private String password;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
      return new UserBuilder();
    }

    public UserBuilder withFirstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public UserBuilder withLastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public UserBuilder withEmail(String email) {
      this.email = email;
      return this;
    }

    public UserBuilder withPreferences(Preferences preferences) {
      this.preferences = preferences;
      return this;
    }

    public UserBuilder withPassword(String password) {
      this.password = password;
      return this;
    }

    public User build() {
      User user = new User();
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setEmail(email);
      user.setPreferences(preferences);
      user.setPassword(password);
      return user;
    }
  }
}
