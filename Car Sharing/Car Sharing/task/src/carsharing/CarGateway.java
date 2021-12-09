package carsharing;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarGateway {
  public static List<Car> findAllByCompanyId(int companyId) {
    var sql = "SELECT * FROM CAR WHERE company_id = ? ORDER BY id ASC";
    return Database.connect(c -> {
      try (var stmt = c.prepareStatement(sql)) {
        stmt.setInt(1, companyId);
        try (var r = stmt.executeQuery()) {
          return extractCarsList(r);
        }
      }
    });
  }

  public static List<Car> findAllNotRentedByCompanyId(int companyId) {
    return Database.connect(c -> {
      var sql = "SELECT * FROM CAR car WHERE car.company_id = ? AND car.id NOT IN (SELECT rented_car_id AS id FROM " +
          "CUSTOMER WHERE rented_car_id IS NOT NULL)";
      try (var stmt = c.prepareStatement(sql)) {
        stmt.setInt(1, companyId);
        try (var r = stmt.executeQuery()) {
          return extractCarsList(r);
        }
      }
    });
  }

  private static List<Car> extractCarsList(ResultSet r) throws SQLException {
    var cars = new ArrayList<Car>();
    while (r.next()) {
      var id = r.getInt("id");
      var name = r.getString("name");
      var cId = r.getInt("company_id");
      cars.add(new Car(id, name, cId));
    }
    return cars;
  }

  public static void save(Car car) {
    Database.connect(c -> {
      var sql = "INSERT INTO CAR (name, company_id) VALUES (?, ?)";
      try (var stmt = c.prepareStatement(sql)) {
        stmt.setString(1, car.getName());
        stmt.setInt(2, car.getCompanyId());
        return stmt.executeUpdate();
      }
    });
  }

  public static Car findById(int id) {
    return Database.connect(c -> {
      var sql = "SELECT * FROM CAR WHERE id = ?";
      try (var stmt = c.prepareStatement(sql)) {
        stmt.setInt(1, id);
        try (var r = stmt.executeQuery()) {
          r.next();
          var name = r.getString("name");
          var companyId = r.getInt("company_id");
          return new Car(id, name, companyId);
        }
      }
    });
  }
}
