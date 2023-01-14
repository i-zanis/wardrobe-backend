package io.noblackhole.wardrobe.wardrobebackend.exception;

public class ItemServiceException extends Exception {
  public ItemServiceException(String message) {
  }

  public ItemServiceException() {
  }

  public ItemServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ItemServiceException(Throwable cause) {
    super(cause);
  }

  protected ItemServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
