package cinema.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@RestController
@ControllerAdvice()
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

  @ResponseBody
  @ExceptionHandler({
      StatisticsController.WrongPasswordException.class,
      PurchaseController.InvalidRowOrColumnException.class,
      PurchaseController.TicketUnavailableException.class,
      ReturnTicketController.InvalidTokenException.class,
  })
  public ResponseEntity<ErrorBody> handleControllerException(HttpServletRequest request, ControllerException ex) {
    return new ResponseEntity<>(new ErrorBody(ex.getMessage()), ex.getStatus());
  }

  static class ErrorBody {
    private final String error;

    public ErrorBody(String error) {
      this.error = error;
    }

    public String getError() {
      return error;
    }
  }
}
