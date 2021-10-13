package cinema.controllers;

import cinema.Room;
import cinema.presentations.ReturnTicketPresentation;
import cinema.presentations.SeatPresentation;
import cinema.usecases.ReturnTicketUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReturnTicketController {

  @Autowired
  private Room room;

  @PostMapping("/return")
  public ReturnTicketPresentation returnTicket(@RequestBody ReturnTicketRequestBody body) {
    var useCase = new ReturnTicketUseCase(room);
    try {
      var seat = useCase.execute(body.token);
      var presentation = new ReturnTicketPresentation();
      presentation.returned_ticket = new SeatPresentation(seat);
      return presentation;
    } catch (ReturnTicketUseCase.InvalidTokenException e) {
      throw new InvalidTokenException(HttpStatus.BAD_REQUEST, "Wrong token!");
    }
  }

  static class ReturnTicketRequestBody {
    public String token;
  }

  public static class InvalidTokenException extends ControllerException {
    public InvalidTokenException(HttpStatus status, String message) {
      super(status, message);
    }
  }
}
