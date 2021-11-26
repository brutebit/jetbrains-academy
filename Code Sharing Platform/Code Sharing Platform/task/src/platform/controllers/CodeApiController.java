package platform.controllers;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import platform.gateways.SnippetGateway;
import platform.usecases.FetchLatestSnippetsUseCase;
import platform.usecases.FetchSnippetUseCase;
import platform.usecases.AddSnippetUseCase;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CodeApiController {
  @Autowired
  private SnippetGateway gateway;

  @GetMapping(value = "/api/code/{UUID}", produces = "application/json")
  public Object fetchSnippet(@PathVariable String UUID) {
    var useCase = new FetchSnippetUseCase(gateway);
    try {
      useCase.execute(UUID);
      return new Object() {
        public final String code = useCase.getSnippet().getCode();
        public final String date = useCase.getSnippet().getLoadDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        public final int time = useCase.getSnippet().timeRemaining();
        public final int views = useCase.getSnippet().viewsRemaining();
      };
    } catch (FetchSnippetUseCase.SnippetNotFoundException e) {
      return ResponseEntity.status(404).build();
    }
  }

  @GetMapping(value = "/api/code/latest", produces = "application/json")
  public List<Object> fetchLatestSnippet() {
    var useCase = new FetchLatestSnippetsUseCase(gateway);
    useCase.execute();
    List<Object> snippets = new ArrayList<>();
    for (var s : useCase.getSnippets()) {
      snippets.add(new Object() {
        public final String code = s.getCode();
        public final String date = s.getLoadDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        public final int time = 0;
        public final int views = 0;
      });
    }
    return snippets;
  }

  @PostMapping(value = "/api/code/new", produces = "application/json")
  public Object addSnippet(@RequestBody AddSnippetRequest body) {
    var useCase = new AddSnippetUseCase(gateway);
    useCase.execute(body.code, body.time, body.views);
    return new Object() {
      public final String id = useCase.getNewSnippet().getId();
    };
  }

  private static class AddSnippetRequest {
    public String code;
    public int time;
    public int views;
  }

  @JsonSerialize
  private static class EmptyResponse {
  }
}
