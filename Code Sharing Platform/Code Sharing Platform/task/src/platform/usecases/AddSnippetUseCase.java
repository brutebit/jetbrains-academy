package platform.usecases;

import platform.entities.Snippet;
import platform.gateways.SnippetGateway;

import java.time.LocalDateTime;
import java.util.UUID;

public class AddSnippetUseCase {
  private Snippet snippet;
  private final SnippetGateway gateway;

  public AddSnippetUseCase(SnippetGateway gateway) {
    this.gateway = gateway;
  }

  public void execute(String code, int time, int views) {
    var snippet = new Snippet();
    snippet.setId(UUID.randomUUID());
    snippet.setCode(code);
    snippet.setLoadDate(LocalDateTime.now());
    snippet.setRestrictTime(time);
    snippet.setRestrictViews(views);
    snippet.setNumberOfViews(0);
    gateway.save(snippet);
    this.snippet = snippet;
  }

  public Snippet getNewSnippet() {
    return this.snippet;
  }
}
