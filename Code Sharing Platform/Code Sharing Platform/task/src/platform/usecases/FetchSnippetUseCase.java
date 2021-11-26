package platform.usecases;

import platform.entities.Snippet;
import platform.gateways.SnippetGateway;

public class FetchSnippetUseCase {
  private Snippet snippet;
  private final SnippetGateway gateway;

  public FetchSnippetUseCase(SnippetGateway gateway) {
    this.gateway = gateway;
  }

  public void execute(String id) throws SnippetNotFoundException {
    var optSnippet = gateway.findById(id);
    if (optSnippet.isEmpty())
      throw new SnippetNotFoundException();

    var snippet = optSnippet.get();

    if (!snippet.isDisplayable())
      throw new SnippetNotFoundException();

    this.snippet = snippet;
    this.snippet.incrementViews();
    gateway.save(this.snippet);
  }

  public Snippet getSnippet() {
    return snippet;
  }

  public static class SnippetNotFoundException extends Exception {
  }
}
