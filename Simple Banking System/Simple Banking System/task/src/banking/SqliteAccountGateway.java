package banking;

import java.sql.*;

public class SqliteAccountGateway implements AccountGateway {
  private final String databasePath;

  public SqliteAccountGateway(String databasePath) {
    this.databasePath = "jdbc:sqlite:" + databasePath;
    String sql = "CREATE TABLE IF NOT EXISTS card (\n"
        + "	id INTEGER PRIMARY KEY,\n"
        + "	number TEXT NOT NULL,\n"
        + "	pin TEXT NOT NULL,\n"
        + " balance INTEGER DEFAULT 0\n"
        + ");";
    connect(conn -> {
      try (Statement stmt = conn.createStatement()) {
        return stmt.executeUpdate(sql);
      }
    });
  }

  @Override
  public boolean checkExistence(String cardNumber) {
    String sql = "SELECT * FROM card WHERE number = ?";
    return connect(conn -> {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, cardNumber);
        return stmt.executeQuery().next();
      }
    });
  }

  @Override
  public Account createAccount(String cardNumber, String pin) {
    String sql = "INSERT into card (number, pin) VALUES (?, ?)";
    return connect(conn -> {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, cardNumber);
        stmt.setString(2, pin);
        stmt.executeUpdate();
        try (ResultSet gk = stmt.getGeneratedKeys()) {
          gk.next();
          int insertId = gk.getInt(1);
          return new Account(insertId, cardNumber, pin, 0);
        }
      }
    });
  }

  @Override
  public Account findAccount(String cardNumber, String pin) {
    String sql = "SELECT * FROM card WHERE number = ? AND pin = ?";
    return connect(conn -> {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, cardNumber);
        stmt.setString(2, pin);
        return extractAccount(stmt);
      }
    });
  }

  @Override
  public Account findAccount(String cardNumber) {
    String sql = "SELECT * FROM card WHERE number = ?";
    return connect(conn -> {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, cardNumber);
        return extractAccount(stmt);
      }
    });
  }

  private Account extractAccount(PreparedStatement stmt) throws SQLException {
    try (ResultSet rs = stmt.executeQuery()) {
      if (rs.next()) {
        int selectedId = rs.getInt("id");
        String selectedCardNumber = rs.getString("number");
        String selectedPin = rs.getString("pin");
        int selectedBalance = rs.getInt("balance");
        return new Account(selectedId, selectedCardNumber, selectedPin, selectedBalance);
      }
      return null;
    }
  }

  @Override
  public void transferBalance(int sourceId, int destinationId, int amount) {
    String subtractBalanceSql = "UPDATE card SET balance = balance - ? WHERE id = ?";
    String addBalanceSql = "UPDATE card SET balance = balance + ? WHERE id = ?";
    transactionalConnect(conn -> {
      try (PreparedStatement stmt = conn.prepareStatement(subtractBalanceSql)) {
        stmt.setInt(1, amount);
        stmt.setInt(2, sourceId);
        stmt.executeUpdate();
      }
      try (PreparedStatement stmt = conn.prepareStatement(addBalanceSql)) {
        stmt.setInt(1, amount);
        stmt.setInt(2, destinationId);
        return stmt.executeUpdate();
      }
    });
  }

  @Override
  public void addBalance(int id, int amount) {
    String sql = "UPDATE card SET balance = balance + ? WHERE id = ?";
    connect(conn -> {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, amount);
        stmt.setInt(2, id);
        return stmt.executeUpdate();
      }
    });
  }

  @Override
  public void deleteAccount(int id) {
    String sql = "DELETE FROM card WHERE id = ?";
    connect(conn -> {
      try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        return stmt.executeUpdate();
      }
    });
  }

  private <T> T connect(ConnectionAction<T> action) {
    try (Connection conn = DriverManager.getConnection(databasePath)) {
      return action.executeWithConnection(conn);
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      System.exit(1);
      return null;
    }
  }

  private <T> T transactionalConnect(ConnectionAction<T> action) {
    Connection conn = null;
    try {
      conn = DriverManager.getConnection(databasePath);
      conn.setAutoCommit(false);
      T result = action.executeWithConnection(conn);
      conn.commit();
      return result;
    } catch (SQLException e) {
      tryRollbackConnection(conn);
      System.err.println(e.getMessage());
      System.exit(1);
      return null;
    }
  }

  private void tryRollbackConnection(Connection conn) {
    if (conn != null) {
      try {
        conn.rollback();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
        System.exit(1);
      }
    }
  }

  @FunctionalInterface
  interface ConnectionAction<T> {
    T executeWithConnection(Connection connection) throws SQLException;
  }
}
