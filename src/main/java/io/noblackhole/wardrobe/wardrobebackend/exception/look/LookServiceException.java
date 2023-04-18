package io.noblackhole.wardrobe.wardrobebackend.exception.look;

public class LookServiceException extends Exception {

  public LookServiceException() {
    super();
  }

  public LookServiceException(String message) {
    super(message);
  }

  public LookServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public LookServiceException(Throwable cause) {
    super(cause);
  }
}
