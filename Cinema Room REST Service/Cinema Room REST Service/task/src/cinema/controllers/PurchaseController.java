package cinema.controllers;

import cinema.Room;
import cinema.presentations.PurchaseTicketPresentation;
import cinema.presentations.SeatPresentation;
import cinema.usecases.PurchaseTicketUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaseController {

  @Autowired
  private Room room;

  @PostMapping("/purchase")
  public PurchaseTicketPresentation purchaseTicket(@RequestBody PurchaseRequest pr) {
    var useCase = new PurchaseTicketUseCase(room);
    try {
      var token = useCase.execute(pr.row, pr.column);
      var presentation = new PurchaseTicketPresentation();
      presentation.ticket = new SeatPresentation(room.getSeatAt(pr.row, pr.column));
      presentation.token = token;
      return presentation;
    } catch (Room.InvalidRowOrColumnException e) {
      throw new InvalidRowOrColumnException();
    } catch (PurchaseTicketUseCase.TicketUnavailableException e) {
      throw new TicketUnavailableException();
    }
  }

  static class PurchaseRequest {
    public int row;
    public int column;
  }

  public static class InvalidRowOrColumnException extends ControllerException {
    public InvalidRowOrColumnException() {
      super(HttpStatus.BAD_REQUEST, "The number of a row or a column is out of bounds!");
    }
  }

  public static class TicketUnavailableException extends ControllerException {
    public TicketUnavailableException() {
      super(HttpStatus.BAD_REQUEST, "The ticket has been already purchased!");
    }
  }

}
