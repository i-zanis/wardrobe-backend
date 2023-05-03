package io.noblackhole.wardrobe.wardrobebackend.exception.item;

public class ItemNotFoundException extends ItemServiceException {
  public ItemNotFoundException() {
  }

  public ItemNotFoundException(String message) {
    super(message);
  }

  public ItemNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ItemNotFoundException(Throwable cause) {
    super(cause);
  }
}
