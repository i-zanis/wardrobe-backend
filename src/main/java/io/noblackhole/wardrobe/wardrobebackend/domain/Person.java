package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jdk.jfr.Timestamp;

import java.time.Instant;

@MappedSuperclass
public class Person extends BaseEntity {
  @Timestamp
  @Column(name = "created_at", nullable = false)
  private final Instant createdAt = Instant.now();
  @Column(name = "first_name")
  @NotBlank(message = "First name is required")
  private String firstName;
  @NotBlank(message = "Last name is required")
  @Column(name = "last_name")
  private String lastName;
  @NotBlank(message = "Email is required")
  @Email(message = "Email must be valid")
  private String email;

  public Person() {

  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
