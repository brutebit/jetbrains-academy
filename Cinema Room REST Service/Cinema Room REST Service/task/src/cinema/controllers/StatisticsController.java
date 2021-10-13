package cinema.controllers;

import cinema.Room;
import cinema.presentations.StatisticsPresentation;
import cinema.usecases.ShowStatisticsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatisticsController {

  @Autowired
  private Room room;

  @PostMapping("/stats")
  public StatisticsPresentation fetchStatistics(@RequestParam Map<String, String> params) {
    if (!params.containsKey("password") ||!params.get("password").equals("super_secret"))
      throw new WrongPasswordException(HttpStatus.UNAUTHORIZED, "The password is wrong!");

    var useCase = new ShowStatisticsUseCase(room);
    useCase.execute();
    var presentation = new StatisticsPresentation();
    presentation.current_income = useCase.getCurrentIncome();
    presentation.number_of_available_seats = useCase.getNumOfAvailableSeats();
    presentation.number_of_purchased_tickets = useCase.getNumOfPurchasedTickets();
    return presentation;
  }

  public static class WrongPasswordException extends ControllerException {
    public WrongPasswordException(HttpStatus status, String message) {
      super(status, message);
    }
  }
}
