package client;

public interface Transaction {
  String method();
  byte[] makeRequest();
  Response makeResponse(byte[] resString);
}
