package platform.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import platform.gateways.SnippetGateway;
import platform.usecases.FetchLatestSnippetsUseCase;
import platform.usecases.FetchSnippetUseCase;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CodeViewController {
  @Autowired
  private SnippetGateway gateway;

  @GetMapping(value = "/code/{UUID}")
  public Object fetchCodeView(Model model, @PathVariable String UUID) {
    var useCase = new FetchSnippetUseCase(gateway);
    try {
      useCase.execute(UUID);
      var snippet = useCase.getSnippet();
      model.addAttribute("code", snippet.getCode());
      model.addAttribute("loadDate", snippet.getLoadDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

      if (snippet.isTimeRestricted()) {
        model.addAttribute("isTimeRestricted", true);
        model.addAttribute("time", snippet.timeRemaining());
      } else {
        model.addAttribute("isTimeRestricted", false);
      }
      if (snippet.isViewRestricted()) {
        model.addAttribute("isViewRestricted", true);
        model.addAttribute("views", snippet.viewsRemaining());
      } else {
        model.addAttribute("isViewRestricted", false);
      }

      return new ModelAndView("snippet");
    } catch (FetchSnippetUseCase.SnippetNotFoundException e) {
      return ResponseEntity.status(404).build();
    }
  }

  @GetMapping(value = "/code/latest")
  public ModelAndView fetchLatestCodeView(Model model) {
    var useCase = new FetchLatestSnippetsUseCase(gateway);
    useCase.execute();
    List<SnippetViewModel> snippets = new ArrayList<>();
    for (var s : useCase.getSnippets()) {
      var viewModel = new SnippetViewModel(s.getCode(), s.getLoadDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
      snippets.add(viewModel);
    }
    model.addAttribute("snippets", snippets);
    return new ModelAndView("latest");
  }

  @GetMapping(value = "/code/new")
  public ModelAndView fetchAddSnippetView(Model model) {
    return new ModelAndView("addSnippet");
  }

  public static class SnippetViewModel {
    public String code;
    public String loadDate;

    public String getCode() {
      return code;
    }

    public String getLoadDate() {
      return loadDate;
    }

    public void setLoadDate(String loadDate) {
      this.loadDate = loadDate;
    }

    public SnippetViewModel(String code, String loadDate) {
      this.code = code;
      this.loadDate = loadDate;
    }
  }
}
