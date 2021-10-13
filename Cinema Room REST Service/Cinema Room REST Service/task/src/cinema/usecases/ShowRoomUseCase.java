package cinema.usecases;

import cinema.Room;
import cinema.presentations.SeatPresentation;

import java.util.ArrayList;
import java.util.List;

public class ShowRoomUseCase {
  private final Room room;
  private List<SeatPresentation> availableSeats;
  private final int totalRows;
  private final int totalColumns;

  public ShowRoomUseCase(Room room) {
    this.room = room;
    totalRows = room.getRows();
    totalColumns = room.getColumns();
  }

  public void execute() throws Room.InvalidRowOrColumnException {
    List<SeatPresentation> availableSeats = new ArrayList<>();
    for (int i = 0; i < room.getRows(); i++)
      for (int j = 0; j < room.getColumns(); j++)
        if (room.isSeatAvailable(i + 1, j + 1))
          availableSeats.add(new SeatPresentation(room.getSeatAt(i + 1, j + 1)));
    this.availableSeats = availableSeats;
  }

  public List<SeatPresentation> getAvailableSeats() {
    return availableSeats;
  }

  public int getTotalRows() {
    return totalRows;
  }

  public int getTotalColumns() {
    return totalColumns;
  }
}
