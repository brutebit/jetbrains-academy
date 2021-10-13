package cinema;

public class Seat {
  private boolean available = true;
  private final int price;
  private final int row;
  private final int column;

  public Seat(int row, int column) {
    this.row = row;
    this.column = column;
    if (row <= 4)
      price = 10;
    else
      price = 8;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public int getPrice() {
    return price;
  }

  public boolean isAvailable() {
    return available;
  }

  public void setAvailable(boolean available) {
    this.available = available;
  }

}
