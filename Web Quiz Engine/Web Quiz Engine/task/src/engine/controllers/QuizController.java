package engine.controllers;

import engine.presentations.AnswerPresentation;
import engine.presentations.CompletionPresentation;
import engine.presentations.QuizPresentation;
import engine.repositories.CompletionRepository;
import engine.repositories.QuizRepository;
import engine.repositories.UserRepository;
import engine.security.UserService;
import engine.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
public class QuizController {

  @Autowired
  private QuizRepository repository;

  @Autowired
  private UserService userService;

  @Autowired
  private CompletionRepository completionRepository;

  @Autowired
  private UserRepository userRepository;

  @GetMapping("/api/quizzes")
  public Page<QuizPresentation> fetchAllQuizzes(
      @RequestParam(defaultValue = "0") String page,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "id") String sortBy) {
    return new FetchQuizzesUseCase(repository).execute(Integer.parseInt(page.substring(0,1)), pageSize, sortBy);
  }

  @GetMapping("/api/quizzes/{id}")
  public QuizPresentation FetchQuizById(@PathVariable long id) {
    try {
      return new FetchQuizUseCase(repository).execute(id);
    } catch (UseCase.QuizNotFoundException e) {
      throw new QuizNotFoundException();
    }
  }

  @GetMapping("/api/quizzes/completed")
  public Page<CompletionPresentation> fetchCompletions(
      @RequestParam(defaultValue = "0") String page,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "completedAt") String sortBy,
      Principal principal) {
    return new FetchCompletionsUseCase(completionRepository, userRepository)
        .execute(Integer.parseInt(page.substring(0,1)), pageSize, sortBy, principal.getName());
  }

  @PostMapping("/api/quizzes/{id}/solve")
  public AnswerPresentation sendAnswerFeedback(
      @PathVariable long id, @Valid @RequestBody SolveQuizReqBody body, Principal principal) {
    try {
      return new FetchAnswerFeedbackUseCase(repository, userRepository, completionRepository)
          .execute(id, body.answer, principal.getName());
    } catch (UseCase.QuizNotFoundException e) {
      throw new QuizNotFoundException();
    }
  }

  @PostMapping("/api/quizzes")
  public QuizPresentation createQuiz(@Valid @RequestBody CreateQuizReqBody body, Principal principal) {
    var useCase = new CreateQuizUseCase(repository, userRepository);
    useCase.setTitle(body.title);
    useCase.setAnswerIndexes(body.answer == null ? new ArrayList<>() : body.answer);
    useCase.setText(body.text);
    useCase.setOptions(body.options);
    return useCase.execute(principal.getName());
  }

  @DeleteMapping("/api/quizzes/{id}")
  public ResponseEntity<QuizPresentation> deleteQuiz(@PathVariable long id, Principal principal) {
    try {
      new DeleteQuizUseCase(repository, userRepository, completionRepository).execute(id, principal.getName());
      return ResponseEntity.noContent().build();
    } catch (UseCase.QuizNotFoundException e) {
      throw new QuizNotFoundException();
    } catch (DeleteQuizUseCase.ForbiddenException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  static class CreateQuizReqBody {
    @NotBlank
    public String title;

    @NotBlank
    public String text;

    @NotNull
    @Size(min = 2)
    public List<String> options;

    public List<Integer> answer;
  }

  static class SolveQuizReqBody {
    @NotNull
    public List<Integer> answer;
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  private static class QuizNotFoundException extends RuntimeException {
  }
}
