package banking;

public class Account {
  private int id;
  private String cardNumber;
  private String pin;
  private int balance;

  public Account(int id, String cardNumber, String pin, int balance) {
    this.id = id;
    this.cardNumber = cardNumber;
    this.pin = pin;
    this.balance = balance;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getPin() {
    return pin;
  }

  public void setPin(String pin) {
    this.pin = pin;
  }

  public int getBalance() {
    return balance;
  }

  public void setBalance(int balance) {
    this.balance = balance;
  }
}
