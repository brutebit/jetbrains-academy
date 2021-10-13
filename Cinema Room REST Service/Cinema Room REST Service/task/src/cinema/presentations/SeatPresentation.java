package cinema.presentations;

import cinema.Seat;

public class SeatPresentation {
  public final int row;
  public final int column;
  public final int price;

  public SeatPresentation(Seat seat) {
    this.row = seat.getRow();
    this.column = seat.getColumn();
    this.price = seat.getPrice();
  }
}
