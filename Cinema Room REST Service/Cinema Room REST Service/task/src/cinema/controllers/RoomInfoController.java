package cinema.controllers;

import cinema.Room;
import cinema.presentations.RoomPresentation;
import cinema.usecases.ShowRoomUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomInfoController {

  @Autowired
  private Room room;

  @GetMapping("/seats")
  public RoomPresentation fetchAvailableSeats() throws Room.InvalidRowOrColumnException {
    var useCase = new ShowRoomUseCase(room);
    useCase.execute();
    var roomPresentation = new RoomPresentation();
    roomPresentation.available_seats = useCase.getAvailableSeats();
    roomPresentation.total_columns = useCase.getTotalColumns();
    roomPresentation.total_rows = useCase.getTotalRows();
    return roomPresentation;
  }
}
