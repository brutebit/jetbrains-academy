package carsharing;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CustomerGateway {

  public static List<Customer> findAll() {
    return Database.connect(c -> {
      try (var stmt = c.createStatement()) {
        var sql = "SELECT * FROM CUSTOMER ORDER BY id ASC";
        try (var r = stmt.executeQuery(sql)) {
          var customers = new ArrayList<Customer>();
          while (r.next()) {
            var id = r.getInt("id");
            var name = r.getString("name");
            var rId = r.getInt("rented_car_id");
            var customer = new Customer(id, name);
            customer.setRentedCarId(rId);
            customers.add(customer);
          }
          return customers;
        }
      }
    });
  }

  public static void save(Customer customer) {
    Database.connect(c -> {
      if (customer.getId() == -1) {
        var sql = "INSERT INTO CUSTOMER (name) VALUES (?)";
        try (var stmt = c.prepareStatement(sql)) {
          stmt.setString(1, customer.getName());
          return stmt.executeUpdate();
        }
      } else {
        var sql = "UPDATE CUSTOMER SET rented_car_id = ? WHERE id = ?";
        try (var stmt = c.prepareStatement(sql)) {
          try {
            stmt.setInt(1, customer.getRentedCarId());
          } catch (NullPointerException e) {
            stmt.setNull(1, Types.NULL);
          }
          stmt.setInt(2, customer.getId());
          return stmt.executeUpdate();
        }
      }
    });
  }
}
