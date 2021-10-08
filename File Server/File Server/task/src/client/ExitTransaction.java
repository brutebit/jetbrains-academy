package client;

import java.nio.charset.StandardCharsets;

public class ExitTransaction implements Transaction {
  @Override
  public String method() {
    return "exit";
  }

  @Override
  public byte[] makeRequest() {
    return method().getBytes();
  }

  @Override
  public Response makeResponse(byte[] resBytes) {
    String resString = new String(resBytes, StandardCharsets.UTF_8);
    int code = Integer.parseInt(resString.split(" ")[0]);
    return new Response(code);
  }
}
