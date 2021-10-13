package cinema.usecases;

import cinema.Room;

public class ShowStatisticsUseCase {
  private final Room room;
  private int currentIncome = 0;
  private int numOfAvailableSeats = 0;
  private int numOfPurchasedTickets = 0;

  public ShowStatisticsUseCase(Room room) {
    this.room = room;
  }

  public void execute() {
    try {
      for (int i = 0; i < room.getRows(); i++)
        for (int j = 0; j < room.getColumns(); j++)
          if (!room.getSeatAt(i + 1, j + 1).isAvailable()) {
            currentIncome += room.getSeatAt(i + 1, j + 1).getPrice();
            numOfPurchasedTickets++;
          } else {
            numOfAvailableSeats++;
          }
    } catch (Room.InvalidRowOrColumnException e) {
    }
  }

  public int getCurrentIncome() {
    return currentIncome;
  }

  public int getNumOfAvailableSeats() {
    return numOfAvailableSeats;
  }

  public int getNumOfPurchasedTickets() {
    return numOfPurchasedTickets;
  }
}
