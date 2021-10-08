package client;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class Main {
  private final static String clientDataPath = System.getProperty("user.dir") + "/src/client/data/";
  private final static String ADDR = "127.0.0.1";
  private final static int PORT = 23456;

  private static Transaction transaction;

  public static void main(String[] args) {
    System.out.print("Enter action (1 - get a file, 2 - save a file, 3 - delete a file): ");
    Scanner scanner = new Scanner(System.in);
    String command = scanner.next();
    try {

      if ("1".equals(command))
        handleGetFile(scanner);
      else if ("2".equals(command))
        handleCreateFile(scanner);
      else if ("3".equals(command))
        handleDeleteFile(scanner);
      else if ("exit".equals(command))
        transaction = new ExitTransaction();
      else {
        System.err.println("Unsupported command!");
        System.exit(1);
      }

      Response response = new TransactionExecutor(transaction).execute(ADDR, PORT);
      System.out.println("The request was sent.");

      String message = "";
      if (response.getCode() == 200) {

        switch (command) {
          case "1":
            System.out.print("The file was downloaded! Specify a name for it: ");
            String filename = scanner.next();
            File file = new File(clientDataPath + filename);
            file.createNewFile();
            Files.write(file.toPath(), response.getContent());
            message = "File saved on the hard drive!";
            break;
          case "2":
            message = "Response says that file is saved! ID = " + response.getContentAsString();
            break;
          case "3":
            message = "The response says that this file was deleted successfully!";
            break;
        }

      }
      else if (response.getCode() == 404)
        message = "The response says that this file is not found!";
      else if (response.getCode() == 403)
        message = "The response says that creating the file was forbidden!";

      System.out.println(message);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  private static void handleDeleteFile(Scanner scanner)
      throws IOException {
    System.out.print("Do you want to get the file by name or by id (1 - name, 2 - id): ");
    String mode = scanner.next();
    ModeTransaction.InputMode inputMode;
    if (mode.equals("1")) {
      inputMode = ModeTransaction.InputMode.BY_NAME;
      System.out.print("Enter filename: ");
    } else {
      inputMode = ModeTransaction.InputMode.BY_ID;
      System.out.print("Enter id: ");
    }
    String userInput = scanner.next();
    transaction = new DeleteTransaction(inputMode, userInput);
  }

  private static void handleGetFile(Scanner scanner)
      throws IOException {
    System.out.print("Do you want to get the file by name or by id (1 - name, 2 - id): ");
    String mode = scanner.next();
    ModeTransaction.InputMode inputMode;
    if (mode.equals("1")) {
      inputMode = ModeTransaction.InputMode.BY_NAME;
      System.out.print("Enter filename: ");
    } else {
      inputMode = ModeTransaction.InputMode.BY_ID;
      System.out.print("Enter id: ");
    }
    String userInput = scanner.next();
    transaction = new GetTransaction(inputMode, userInput);
  }

  private static void handleCreateFile(Scanner scanner) throws IOException {
    System.out.print("Enter name of the file: ");
    String filename = scanner.next();
    String filenameOnServer;
    byte[] content;

    File file = new File(clientDataPath + filename);
    if (file.exists()) {
      scanner.nextLine();
      System.out.print("Enter name of the file to be saved on server: ");
      filenameOnServer = scanner.nextLine();
      if (filenameOnServer.trim().isEmpty()) {
        filenameOnServer = filename;
      }
      content = Files.readAllBytes(file.toPath());
    } else {
      filenameOnServer = filename;
      System.out.print("Enter file content: ");
      scanner.nextLine();
      content = scanner.nextLine().getBytes();
    }
    transaction = new PutTransaction(filenameOnServer, content);
  }
}
