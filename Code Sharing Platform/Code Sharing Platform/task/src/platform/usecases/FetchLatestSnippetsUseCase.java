package platform.usecases;

import platform.entities.Snippet;
import platform.gateways.SnippetGateway;

import java.util.ArrayList;
import java.util.List;

public class FetchLatestSnippetsUseCase {
  private final List<Snippet> snippets = new ArrayList<>();
  private final SnippetGateway gateway;

  public FetchLatestSnippetsUseCase(SnippetGateway gateway) {
    this.gateway = gateway;
  }

  public void execute() {
    List<Snippet> dbSnippets = gateway.findTop10ByIsRestrictedOrderByLoadDateDesc(false);
    snippets.addAll(dbSnippets);
  }

  public List<Snippet> getSnippets() {
    return snippets;
  }
}
