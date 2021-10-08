package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

public class Main {
  private final static String filesPath = System.getProperty("user.dir") + "/src/server/data/";
  private final static String mapPath = filesPath + "map_obj";
  private static FileIdMap map;

  public static void main(String[] args) {
    System.out.println("Server started!");
    String address = "127.0.0.1";
    int port = 23456;

    try {
      Object deserialized = SerializationUtils.deserialize(mapPath);
      map = (FileIdMap) deserialized;
    } catch (IOException | ClassNotFoundException e) {
      map = new FileIdMap();
    }

    try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address))) {
      while (true) {
        try (Socket socket = server.accept();
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

          int messageLength = input.readInt();
          byte[] message = new byte[messageLength];
          input.readFully(message, 0, messageLength);

          String messageStr = new String(message, StandardCharsets.UTF_8);
          String[] messageSplit = messageStr.split(" ");
          String verb = messageSplit[0];

          if (verb.equals("exit")) {
            SerializationUtils.serialize(map, mapPath);
            writeCode(output, "200");
            return;
          }

          switch (verb) {
            case "PUT":
              handlePutRequest(output, messageLength, message, messageSplit);
              break;
            case "GET":
              handleGetRequest(output, messageSplit);
              break;
            case "DELETE":
              handleDeleteRequest(output, messageSplit);
              break;
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private static void handlePutRequest(DataOutputStream output, int messageLength, byte[] message, String[] messageSplit)
      throws IOException {
    File file;
    String filename;
    try {
      filename = messageSplit[1];
      file = new File(filesPath + filename);
      Files.createFile(file.toPath());
      int startIndex = "PUT".length() + filename.length() + 2;
      int size = message.length - startIndex;
      byte[] content = new byte[size];
      for (int i = startIndex, j = 0; i < messageLength; i++, j++)
        content[j] = message[i];

      try (FileOutputStream fos = new FileOutputStream(file)) {
        fos.write(content);
        output.writeInt(("200 " + map.put(filename)).getBytes().length);
        output.write(("200 " + map.put(filename)).getBytes());
      }
    } catch (FileAlreadyExistsException e) {
      writeCode(output, "403");
    }
  }

  private static void handleDeleteRequest(DataOutputStream output, String[] messageSplit) throws IOException {
    String fetchMethod;
    File file;
    String filename;
    fetchMethod = messageSplit[1];
    if (fetchMethod.equals("BY_ID"))
      filename = map.get(Integer.parseInt(messageSplit[2]));
    else
      filename = messageSplit[2];
    file = new File(filesPath + filename);
    if (file.exists()) {
      if (file.delete()) {
        writeCode(output, "200");
        if (fetchMethod.equals("BY_ID"))
          map.remove(Integer.parseInt(messageSplit[2]));
        else
          map.remove(map.getKey(filename));
      }
    } else {
      writeCode(output, "404");
    }
  }

  private static void handleGetRequest(DataOutputStream output, String[] messageSplit) throws IOException {
    String fetchMethod;
    File file;
    String filename;
    fetchMethod = messageSplit[1];
    if (fetchMethod.equals("BY_ID"))
      filename = map.get(Integer.parseInt(messageSplit[2]));
    else
      filename = messageSplit[2];
    file = new File(filesPath + filename);
    if (file.exists()) {
      byte[] content = Files.readAllBytes(file.toPath());
      byte[] firstPart = "200 ".getBytes();
      byte[] combined = new byte[firstPart.length + content.length];
      for (int i = 0; i < combined.length; ++i)
        combined[i] = i < firstPart.length ? firstPart[i] : content[i - firstPart.length];
      output.writeInt(combined.length);
      output.write(combined);
    } else {
      writeCode(output, "404");
    }
  }

  private static void writeCode(DataOutputStream output, String s) throws IOException {
    output.writeInt(s.getBytes().length);
    output.write(s.getBytes());
  }
}
