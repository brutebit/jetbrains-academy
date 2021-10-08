package client;

import java.nio.charset.StandardCharsets;

class DeleteTransaction extends ModeTransaction {
  public DeleteTransaction(ModeTransaction.InputMode mode, String nameOrId) {
    super(mode, nameOrId);
  }

  @Override
  public String method() {
    return "DELETE";
  }

  @Override
  public Response makeResponse(byte[] resBytes) {
    String resString = new String(resBytes, StandardCharsets.UTF_8);
    int code = Integer.parseInt(resString.split(" ")[0]);
    return new Response(code);
  }
}
