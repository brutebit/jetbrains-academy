package carsharing;

import java.util.ArrayList;
import java.util.List;

public class CompanyGateway {

  public static List<Company> findAll() {
    return Database.connect(c -> {
      try (var stmt = c.createStatement()) {
        var sql = "SELECT * FROM COMPANY ORDER BY id ASC";
        try (var r = stmt.executeQuery(sql)) {
          var companies = new ArrayList<Company>();
          while (r.next()) {
            var id = r.getInt("id");
            var name = r.getString("name");
            companies.add(new Company(id, name));
          }
          return companies;
        }
      }
    });
  }

  public static Company findById(int id) {
    return Database.connect(c -> {
      var sql = "SELECT * FROM COMPANY WHERE id = ?";
      try (var stmt = c.prepareStatement(sql)) {
        stmt.setInt(1, id);
        try (var r = stmt.executeQuery()) {
          r.next();
          var name = r.getString("name");
          return new Company(id, name);
        }
      }
    });
  }

  public static void save(Company company) {
    Database.connect(c -> {
      var sql = "INSERT INTO COMPANY (name) VALUES (?)";
      try (var stmt = c.prepareStatement(sql)) {
        stmt.setString(1, company.getName());
        return stmt.executeUpdate();
      }
    });
  }
}
