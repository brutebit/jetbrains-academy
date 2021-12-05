package advisor;

import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Queue;

import static advisor.Store.*;

public class FetchTokenCommand extends Command {

  public FetchTokenCommand(Queue<Command> commands) {
    super(commands);
  }

  @Override
  void execute() throws IOException, InterruptedException {
    System.out.println("making http request for access_token...");
    var client = HttpClient.newBuilder().build();
    var authStr = clientId + ":" + clientSecret;
    var request = HttpRequest.newBuilder()
        .header("Content-Type", "application/x-www-form-urlencoded")
        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString(authStr.getBytes()))
        .uri(URI.create(serverPath + "/api/token"))
        .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&" +
            "redirect_uri=" + redirectUri + "&code=" + code))
        .build();
    var response = client.send(request, HttpResponse.BodyHandlers.ofString());
    System.out.println("response:");
    var jo = JsonParser.parseString(response.body()).getAsJsonObject();
    accessToken = jo.get("access_token").getAsString();
    System.out.println("Success!");
    commands.add(new UserInputCommand(commands));
  }
}
