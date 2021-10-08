package client;

import java.nio.charset.StandardCharsets;

public class PutTransaction implements Transaction {
  private final String name;
  private final byte[] content;

  public PutTransaction(String name, byte[] content) {
    this.name = name;
    this.content = content;
  }

  @Override
  public String method() {
    return "PUT";
  }

  @Override
  public byte[] makeRequest() {
    byte[] firstPart = (method() + " " + name + " ").getBytes();
    byte[] secondPart = content;
    byte[] request = new byte[firstPart.length + secondPart.length];
    for (int i = 0; i < request.length; ++i)
      request[i] = i < firstPart.length ? firstPart[i] : secondPart[i - firstPart.length];
    return request;
  }

  @Override
  public Response makeResponse(byte[] resBytes) {
    String resString = new String(resBytes, StandardCharsets.UTF_8);
    int code = Integer.parseInt(resString.split(" ")[0]);
    Response response = new Response(code);
    if (code == 200) {
      String id = resString.substring(4);
      response.setContent(id.getBytes(StandardCharsets.UTF_8));
    }
    return response;
  }
}
