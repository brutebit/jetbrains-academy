package banking;

public class Bank {
  private final AccountGateway accountGateway;
  private final CardNumberGenerator cardNumberGenerator;
  private Account loggedInAccount = null;

  public Bank(String dbPath) {
    this.accountGateway = new SqliteAccountGateway(dbPath);
    this.cardNumberGenerator = new CardNumberGenerator(accountGateway);
  }

  public Account createAccount() {
    String cardNumber = cardNumberGenerator.generateCardNumber();
    String pin = cardNumberGenerator.generatePin();
    return accountGateway.createAccount(cardNumber, pin);
  }

  public void login(String cardNumber, String pin) throws InvalidCredentialException {
    loggedInAccount = accountGateway.findAccount(cardNumber, pin);
    if (loggedInAccount == null)
      throw new InvalidCredentialException();
  }

  public void logout() {
    loggedInAccount = null;
  }

  public void addBalance(int amount) {
    accountGateway.addBalance(loggedInAccount.getId(), amount);
    loggedInAccount.setBalance(loggedInAccount.getBalance() + amount);
  }

  public void transferBalance(String cardNumber, int amount) throws BalanceNotEnoughException {
    Account destinationAccount = accountGateway.findAccount(cardNumber);

    if (loggedInAccount.getBalance() < amount)
      throw new BalanceNotEnoughException();

    accountGateway.transferBalance(loggedInAccount.getId(), destinationAccount.getId(), amount);
    loggedInAccount.setBalance(loggedInAccount.getBalance() - amount);
  }

  public void validateCardNumber(String cardNumber) throws InvalidCardNumberException, CardNumberNotFoundException {
    LuhnAlgorithm algorithm = new LuhnAlgorithm(cardNumber);
    String checksum = algorithm.getChecksumDigit();

    if (!checksum.equals(String.valueOf(cardNumber.charAt(cardNumber.length() - 1))))
      throw new InvalidCardNumberException();

    Account destinationAccount = accountGateway.findAccount(cardNumber);
    if (destinationAccount == null)
      throw new CardNumberNotFoundException();
  }

  public Account getLoggedInAccount() {
    return loggedInAccount;
  }

  public void closeAccount() {
    accountGateway.deleteAccount(loggedInAccount.getId());
    logout();
  }

  public static class InvalidCredentialException extends Exception {
  }

  public static class CardNumberNotFoundException extends Exception {
  }

  public static class InvalidCardNumberException extends Exception {
  }

  public static class BalanceNotEnoughException extends Exception {
  }
}
