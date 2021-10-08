package client;

import java.nio.charset.StandardCharsets;

class GetTransaction extends ModeTransaction {
  public GetTransaction(ModeTransaction.InputMode mode, String nameOrId) {
    super(mode, nameOrId);
  }

  @Override
  public String method() {
    return "GET";
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
