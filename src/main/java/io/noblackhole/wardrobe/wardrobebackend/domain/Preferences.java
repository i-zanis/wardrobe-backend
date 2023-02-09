package io.noblackhole.wardrobe.wardrobebackend.domain;

import jakarta.persistence.Entity;

@Entity
public class Preferences extends BaseEntity {
  String location;
  Boolean isDarkMode;
  Boolean isMetric;
  Boolean isNotificationsEnabled;

  public Preferences(String location, Boolean isDarkMode, Boolean isMetric, Boolean isNotificationsEnabled) {
    this.location = location;
    this.isDarkMode = isDarkMode;
    this.isMetric = isMetric;
    this.isNotificationsEnabled = isNotificationsEnabled;
  }

  public Preferences(Long id, String location, Boolean isDarkMode, Boolean isMetric, Boolean isNotificationsEnabled) {
    super(id);
    this.location = location;
    this.isDarkMode = isDarkMode;
    this.isMetric = isMetric;
    this.isNotificationsEnabled = isNotificationsEnabled;
  }

  public Preferences() {

  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Boolean getDarkMode() {
    return isDarkMode;
  }

  public void setDarkMode(Boolean darkMode) {
    isDarkMode = darkMode;
  }

  public Boolean getMetric() {
    return isMetric;
  }

  public void setMetric(Boolean metric) {
    isMetric = metric;
  }

  public Boolean getNotificationsEnabled() {
    return isNotificationsEnabled;
  }

  public void setNotificationsEnabled(Boolean notificationsEnabled) {
    isNotificationsEnabled = notificationsEnabled;
  }
}
