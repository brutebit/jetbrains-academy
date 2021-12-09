package carsharing;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static carsharing.Config.databaseName;

public class Database {

  public static void create () {
    try {
      Class.forName("org.h2.Driver");
    } catch (ClassNotFoundException e) {
      System.err.println("JDBC Driver Not Found!");
      System.exit(1);
    }

    var dir = new File("./src/carsharing/db/");
    dir.mkdirs();
    var createCompanySql = "CREATE TABLE IF NOT EXISTS COMPANY (" +
        "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
        "name VARCHAR(255) UNIQUE NOT NULL, " +
        "PRIMARY KEY ( id )" +
        ")";
    var createCarSql = "CREATE TABLE IF NOT EXISTS CAR (" +
        "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
        "name VARCHAR(255) UNIQUE NOT NULL, " +
        "company_id INT NOT NULL," +
        "FOREIGN KEY (company_id) REFERENCES COMPANY(id)" +
        ")";
    var createCustomerSql = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
        "id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
        "name VARCHAR(255) UNIQUE NOT NULL, " +
        "rented_car_id INT DEFAULT NULL," +
        "FOREIGN KEY (rented_car_id) REFERENCES CAR(id)" +
        ")";
    connect(conn -> {
      try (Statement stmt = conn.createStatement()) {
        stmt.executeUpdate(createCompanySql);
        stmt.executeUpdate(createCarSql);
        return stmt.executeUpdate(createCustomerSql);
      }
    });
  }

  public static <T> T connect(ConnectionAction<T> action) {
    try (Connection conn = DriverManager.getConnection("jdbc:h2:./src/carsharing/db/" + databaseName)) {
      conn.setAutoCommit(true);
      return action.executeWithConnection(conn);
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      System.exit(1);
      return null;
    }
  }

  @FunctionalInterface
  interface ConnectionAction<T> {
    T executeWithConnection(Connection connection) throws SQLException;
  }
}
