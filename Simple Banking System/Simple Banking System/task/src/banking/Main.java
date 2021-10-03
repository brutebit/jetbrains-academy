package banking;

import java.util.Scanner;

public class Main {
  private static Mode mode = Mode.LOGIN_MODE;
  private final static String loginMenu =
      "\n1. Create an account\n" +
          "2. Log into account\n" +
          "0. Exit\n";
  private final static String accountMenu =
      "\n1. Balance\n" +
          "2. Add income\n" +
          "3. Do transfer\n" +
          "4. Close account\n" +
          "5. Log out\n" +
          "0. Exit\n";
  private static Bank bank;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String dbPath = "";

    if (args.length >= 2) {
      if (args[0].equals("-fileName"))
        dbPath = args[1];
    } else {
      System.err.println("Specify path to the database with -fileName <name>");
      System.exit(1);
    }

    bank = new Bank(dbPath);
    int choice;

    do {
      System.out.println(mode == Mode.LOGIN_MODE ? loginMenu : accountMenu);
      choice = scanner.nextInt();

      if (mode == Mode.LOGIN_MODE)
        handleLoginMode(scanner, choice);
      else
        handleAccountMode(scanner, choice);

    } while (choice != 0);

    scanner.close();
    System.out.println("\nBye!\n");
  }

  private static void handleLoginMode(Scanner scanner, int choice) {
    switch (choice) {
      case 1:
        createAccount();
        break;
      case 2:
        handleLogin(scanner);
        break;
    }
  }

  private static void createAccount() {
    Account account = bank.createAccount();
    System.out.println("\nYour card has been created");
    System.out.println("Your card number:");
    System.out.println(account.getCardNumber());
    System.out.println("Your card PIN:");
    System.out.println(account.getPin());
  }

  private static void handleLogin(Scanner scanner) {
    System.out.println("\nEnter your card number:");
    String cardNumber = scanner.next();
    System.out.println("Enter your PIN:");
    String pin = scanner.next();

    try {
      bank.login(cardNumber, pin);
      System.out.println("\nYou have successfully logged in!");
      mode = Mode.ACC_MODE;
    } catch (Bank.InvalidCredentialException e) {
      System.out.println("\nWrong card number or PIN!");
    }
  }

  private static void handleAccountMode(Scanner scanner, int choice) {
    switch (choice) {
      case 1:
        System.out.println("\nBalance: " + bank.getLoggedInAccount().getBalance());
        break;
      case 2:
        System.out.println("\nEnter income:");
        int income = scanner.nextInt();
        bank.addBalance(income);
        System.out.println("Income was added!");
        break;
      case 3:
        handleTransfer(scanner);
        break;
      case 4:
        bank.closeAccount();
        System.out.println("The account has been closed!");
        mode = Mode.LOGIN_MODE;
        break;
      case 5:
        bank.logout();
        System.out.println("\nYou have successfully logged out!");
        mode = Mode.LOGIN_MODE;
        break;
    }
  }

  private static void handleTransfer(Scanner scanner) {
    System.out.println("Transfer");
    System.out.println("Enter card number:");
    String cardNumber = scanner.next();
    try {
      bank.validateCardNumber(cardNumber);
      System.out.println("Enter how much money you want to transfer:");
      int amount = scanner.nextInt();
      bank.transferBalance(cardNumber, amount);
      System.out.println("Success!");
    } catch (Bank.InvalidCardNumberException e) {
      System.out.println("Probably you made a mistake in the card number. Please try again!");
    } catch (Bank.CardNumberNotFoundException e) {
      System.out.println("Such a card does not exist.");
    } catch (Bank.BalanceNotEnoughException e) {
      System.out.println("Not enough money!");
    }
  }

  private enum Mode {
    LOGIN_MODE, ACC_MODE
  }
}
