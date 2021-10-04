package banking;

public class LuhnAlgorithm {
  private final String cardNumber;

  public LuhnAlgorithm(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getChecksumDigit() {
    String toProcessNumber = String.valueOf(cardNumber);
    if (cardNumber.length() == 16)
      toProcessNumber = cardNumber.substring(0, 15);

    int[] digits = processDigits(toProcessNumber);
    int total = 0;
    for (int d : digits)
      total += d;
    return "" + getStepsToMultipleOfTen(total);
  }

  private int getStepsToMultipleOfTen(int total) {
    int steps = 0;
    while (total % 10 != 0) {
      steps++;
      total++;
    }
    return steps;
  }

  private int[] processDigits(String number) {
    int[] digits = new int[number.length()];
    for (int i = 0; i < number.length(); i++) {
      int digit = Integer.parseInt(String.valueOf(number.charAt(i)));
      if ((i + 1) % 2 != 0) {
        int digitDouble = digit * 2;
        if (digitDouble > 9) {
          digits[i] = digitDouble - 9;
        } else {
          digits[i] = digitDouble;
        }
      } else {
        digits[i] = digit;
      }
    }
    return digits;
  }
}
