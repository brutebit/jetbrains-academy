package client;

import java.nio.charset.StandardCharsets;

class Response {
  private final int code;
  private byte[] content;

  public Response(int code) {
    this.code = code;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public String getContentAsString() {
    return new String(content, StandardCharsets.UTF_8);
  }

  public byte[] getContent() {
    return content;
  }

  public int getCode() {
    return code;
  }

}
