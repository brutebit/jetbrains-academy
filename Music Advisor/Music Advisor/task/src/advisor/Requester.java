package advisor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static advisor.Store.accessToken;
import static advisor.Store.resourceUri;

public class Requester {
  public static String fetch(String path, Integer offset, Integer limit) throws IOException, InterruptedException {
    String query = "";
    if (offset != null && limit != null)
      query += "?offset=" + offset + "&limit=" + limit;
    else if (offset != null)
      query += "?offset=" + offset;
    else if (limit != null)
      query += "?limit=" + limit;

    return fetch(resourceUri + "/v1/browse/" + path + query);
  }

  public static String fetch(String path) throws IOException, InterruptedException {
    var client = HttpClient.newBuilder().build();
    var request = HttpRequest.newBuilder()
        .header("Authorization", "Bearer " + accessToken)
        .uri(URI.create(path))
        .GET()
        .build();
    var response = client.send(request, HttpResponse.BodyHandlers.ofString());
    return response.body();
  }
}
