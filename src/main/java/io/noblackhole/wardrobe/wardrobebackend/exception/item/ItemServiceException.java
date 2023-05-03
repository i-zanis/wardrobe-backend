package io.noblackhole.wardrobe.wardrobebackend.exception.item;

public class ItemServiceException extends Exception {

  public ItemServiceException() {
    super();
  }

  public ItemServiceException(String message) {
    super(message);
  }

  public ItemServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public ItemServiceException(Throwable cause) {
    super(cause);
  }
}
