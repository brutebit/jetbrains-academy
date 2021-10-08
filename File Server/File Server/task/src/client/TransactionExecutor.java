package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class TransactionExecutor {
  final Transaction transaction;

  public TransactionExecutor(Transaction transaction) {
    this.transaction = transaction;
  }

  public Response execute(String address, int port) throws IOException {
    try (
        Socket socket = new Socket(InetAddress.getByName(address), port);
        DataInputStream input = new DataInputStream(socket.getInputStream());
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
    ) {
      byte[] request = transaction.makeRequest();
      output.writeInt(request.length);
      output.write(request);

      int length = input.readInt();
      byte[] message = new byte[length];
      input.readFully(message, 0, message.length);
      return transaction.makeResponse(message);
    }
  }
}
