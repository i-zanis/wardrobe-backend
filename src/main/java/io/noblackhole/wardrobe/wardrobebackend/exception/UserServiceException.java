package io.noblackhole.wardrobe.wardrobebackend.exception;

import org.springframework.dao.DataAccessException;

public class UserServiceException extends Exception {
  public UserServiceException(String message) {
    super(message);
  }

  public UserServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserServiceException(String s, DataAccessException e, String message) {

  }
}