package advisor;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import static advisor.Store.*;

public class AuthCommand extends Command {

  public AuthCommand(Queue<Command> commands) {
    super(commands);
  }

  public void execute() throws IOException {
    System.out.println("use this link to request the access code:");
    System.out.println(serverPath + "/authorize?client_id=" + clientId +
        "&redirect_uri=" + redirectUri + "&response_type=code");
    System.out.println("waiting for code...");

    var server = HttpServer.create();
    server.bind(new InetSocketAddress(8080), 0);

    server.createContext("/", exchange -> {
      var query = exchange.getRequestURI().getQuery();
      var params = extractQueryParams(query);

      String message;
      boolean gotTheCode = false;
      if (params.containsKey("code")) {
        message = "Got the code. Return back to your program.";
        System.out.println("code received");
        code = params.get("code");
        gotTheCode = true;
      } else {
        message = "Authorization code not found. Try again.";
      }

      exchange.sendResponseHeaders(200, message.length());
      exchange.getResponseBody().write(message.getBytes());
      exchange.getResponseBody().close();
      if (gotTheCode) {
        isLoggedIn = true;
        commands.add(new FetchTokenCommand(commands));
        server.stop(0);
      }
    });
    server.start();
  }

  public Map<String, String> extractQueryParams(String query) {
    var paramMap = new HashMap<String, String>();
    if (query != null && !query.isBlank()) {
      if (query.contains("code")) {
        var params = query.split("&");
        for (var p : params) {
          var keyValue = p.split("=");
          paramMap.put(keyValue[0], keyValue[1]);
        }
      }
    }
    return paramMap;
  }
}
