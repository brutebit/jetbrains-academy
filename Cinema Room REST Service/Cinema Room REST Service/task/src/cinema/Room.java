package cinema;

public class Room {
  private final int rows;
  private final int columns;
  private final Seat[][] seats;

  public Room(int rows, int columns) {
    this.rows = rows;
    this.columns = columns;
    seats = new Seat[rows][columns];

    for (int i = 0; i < rows; i++)
      for (int j = 0; j < columns; j++)
          seats[i][j] = new Seat(i + 1, j + 1);
  }

  public Seat getSeatAt(int row, int column) throws InvalidRowOrColumnException {
    try {
      return seats[row - 1][column - 1];
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new InvalidRowOrColumnException();
    }
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

  public boolean isSeatAvailable(int row, int column) throws InvalidRowOrColumnException {
    try {
      return seats[row - 1][column - 1].isAvailable();
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new InvalidRowOrColumnException();
    }
  }

  public void purchaseSeat(int row, int column) throws InvalidRowOrColumnException {
    try {
      seats[row - 1][column - 1].setAvailable(false);;
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new InvalidRowOrColumnException();
    }
  }

  public void returnSeat(int row, int column) throws InvalidRowOrColumnException {
    try {
      seats[row - 1][column - 1].setAvailable(true);
    } catch (Exception e) {
      throw new InvalidRowOrColumnException();
    }
  };

  static public class InvalidRowOrColumnException extends Exception {
  }
}
