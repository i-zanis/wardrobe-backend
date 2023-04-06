package io.noblackhole.wardrobe.wardrobebackend.exception;

import io.noblackhole.wardrobe.wardrobebackend.exception.item.ItemNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.item.ItemServiceException;
import io.noblackhole.wardrobe.wardrobebackend.exception.user.UserNotFoundException;
import io.noblackhole.wardrobe.wardrobebackend.exception.user.UserServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<String> handleDataAccessException(DataAccessException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(e.getMessage());
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(e.getMessage());
  }

  @ExceptionHandler(UserServiceException.class)
  public ResponseEntity<String> handleUserServiceException(UserServiceException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(e.getMessage());
  }

  @ExceptionHandler(ItemServiceException.class)
  public ResponseEntity<String> handleItemServiceException(ItemServiceException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(e.getMessage());
  }

  @ExceptionHandler(ItemNotFoundException.class)
  public ResponseEntity<String> handleItemNotFoundException(ItemNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(e.getMessage());
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
    Map<String, Object> errors = new HashMap<>(8);
    e.getBindingResult()
      .getAllErrors()
      .forEach(error -> {
        String fieldName = ((FieldError) error).getField();
        String errorMessage = error.getDefaultMessage();
        errors.put(fieldName, errorMessage);
      });
    ErrorResponse errorResponse = new ErrorResponse(request.getRequestURI(), errors);
    logger.error("MethodArgumentNotValidException:" + e.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(errorResponse);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
      .body(e.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(e.getMessage());
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
      .body(e.getMessage());
  }

  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  public ResponseEntity<String> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
      .body(e.getMessage());
  }

}