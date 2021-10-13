package cinema.controllers;

import org.springframework.http.HttpStatus;

public abstract class ControllerException extends RuntimeException {
  protected final HttpStatus status;

  public ControllerException(HttpStatus status, String message) {
    super(message);
    this.status = status;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
