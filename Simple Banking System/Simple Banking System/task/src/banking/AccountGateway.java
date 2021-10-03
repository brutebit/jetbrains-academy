package banking;

public interface AccountGateway {
  boolean checkExistence(String cardNumber);
  Account createAccount(String cardNumber, String pin);
  Account findAccount(String cardNumber, String pin);
  Account findAccount(String cardNumber);
  void transferBalance(int sourceId, int destinationId, int amount);
  void addBalance(int id, int amount);
  void deleteAccount(int id);
}
