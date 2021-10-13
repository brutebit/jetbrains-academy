package cinema.usecases;

import cinema.Room;
import cinema.Seat;
import cinema.TokenRegistrar;

public class ReturnTicketUseCase {
  private final Room room;

  public ReturnTicketUseCase(Room room) {
    this.room = room;
  }

  public Seat execute(String token) throws InvalidTokenException {
    var seat = TokenRegistrar.getSeat(token);
    if (seat == null)
      throw new InvalidTokenException();
    TokenRegistrar.unregisterToken(seat);
    try {
      room.returnSeat(seat.getRow(), seat.getColumn());
    } catch (Room.InvalidRowOrColumnException e) {
      e.printStackTrace();
    }
    return seat;
  }

  public static class InvalidTokenException extends Throwable {
  }
}
