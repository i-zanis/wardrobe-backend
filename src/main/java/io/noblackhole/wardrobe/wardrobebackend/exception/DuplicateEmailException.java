package io.noblackhole.wardrobe.wardrobebackend.exception;

public class DuplicateEmailException extends RuntimeException {
  public DuplicateEmailException(String s) {
  }

  public DuplicateEmailException() {
  }

  public DuplicateEmailException(String message, Throwable cause) {
    super(message, cause);
  }

  public DuplicateEmailException(Throwable cause) {
    super(cause);
  }

  protected DuplicateEmailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
