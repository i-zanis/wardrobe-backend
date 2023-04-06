package io.noblackhole.wardrobe.wardrobebackend.exception.item;

public class ItemNotFoundException extends Exception {
  public ItemNotFoundException(String message) {
    super(message);
  }

  public ItemNotFoundException() {
  }

  public ItemNotFoundException(String format, ItemNotFoundException e) {
  }

  public ItemNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ItemNotFoundException(Throwable cause) {
    super(cause);
  }

  protected ItemNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
