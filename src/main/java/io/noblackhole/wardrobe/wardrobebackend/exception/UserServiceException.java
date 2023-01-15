package io.noblackhole.wardrobe.wardrobebackend.exception;

import org.springframework.dao.DataAccessException;

public class UserServiceException extends Exception {
  public UserServiceException(String message) {
    super(message);
  }

  public UserServiceException() {
  }

  public UserServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserServiceException(Throwable cause) {
    super(cause);
  }

  public UserServiceException(DataAccessException e) {
    super(e);
  }

  public UserServiceException(String message, DataAccessException e) {
    super(message, e);
  }

}