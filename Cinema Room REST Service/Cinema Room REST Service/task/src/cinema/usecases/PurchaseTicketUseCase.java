package cinema.usecases;

import cinema.Room;
import cinema.TokenRegistrar;

public class PurchaseTicketUseCase {
  private final Room room;

  public PurchaseTicketUseCase(Room room) {
    this.room = room;
  }

  public String execute(int row, int column) throws Room.InvalidRowOrColumnException, TicketUnavailableException {
    if (room.isSeatAvailable(row, column)) {
      var token = TokenRegistrar.generateToken(room.getSeatAt(row, column));
      room.purchaseSeat(row, column);
      return token;
    }

    throw new TicketUnavailableException();
  }

  static public class TicketUnavailableException extends Exception {
  }
}
