package banking;

import java.util.Random;

public class CardNumberGenerator {
  private static final String BIN = "400000";
  private static final int NUM_PIN_VALUES = 10_000;
  private static final int NUM_ACC_ID_VALUES = 1_000_000_000;
  private final AccountGateway accountGateway;

  public CardNumberGenerator(AccountGateway accountGateway) {
    this.accountGateway = accountGateway;
  }

  public String generateCardNumber() {
    String numberWithoutChecksum = BIN + generateAccountId();
    String cardNumber = numberWithoutChecksum + generateChecksum(numberWithoutChecksum);
    if (accountGateway.checkExistence(cardNumber)) {
      return generateCardNumber();
    }
    return cardNumber;
  }

  public String generatePin() {
    Random random = new Random();
    return String.format("%04d", random.nextInt(NUM_PIN_VALUES));
  }

  private String generateAccountId() {
    Random random = new Random();
    return String.format("%09d", random.nextInt(NUM_ACC_ID_VALUES));
  }

  private String generateChecksum(String numberWithoutChecksum) {
    LuhnAlgorithm algorithm = new LuhnAlgorithm(numberWithoutChecksum);
    return algorithm.getChecksumDigit();
  }
}
