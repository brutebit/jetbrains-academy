package client;

abstract class ModeTransaction implements Transaction {
  private final ModeTransaction.InputMode mode;
  private final String nameOrId;

  protected ModeTransaction(InputMode mode, String nameOrId) {
    this.mode = mode;
    this.nameOrId = nameOrId;
  }

  @Override
  public byte[] makeRequest() {
    String request;
    if (this.mode == InputMode.BY_ID) {
      request = method() + " BY_ID " + nameOrId;
    } else {
      request = method() + " BY_NAME " + nameOrId;
    }
    return request.getBytes();
  }

  public enum InputMode {
    BY_ID, BY_NAME
  }
}
