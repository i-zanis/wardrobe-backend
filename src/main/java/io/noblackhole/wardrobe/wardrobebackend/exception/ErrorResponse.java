package io.noblackhole.wardrobe.wardrobebackend.exception;

import java.util.Map;

public class ErrorResponse {
  private String message;
  private Map<String, Object> errors;

  public ErrorResponse(String message, Map<String, Object> errors) {
    this.message = message;
    this.errors = errors;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Map<String, Object> getErrors() {
    return errors;
  }

  public void setErrors(Map<String, Object> errors) {
    this.errors = errors;
  }
}
