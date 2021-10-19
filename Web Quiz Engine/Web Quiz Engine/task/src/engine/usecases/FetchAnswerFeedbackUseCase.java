package engine.usecases;

import engine.entities.Completion;
import engine.entities.Quiz;
import engine.repositories.CompletionRepository;
import engine.repositories.QuizRepository;
import engine.presentations.AnswerPresentation;
import engine.repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

public class FetchAnswerFeedbackUseCase extends UseCase<Quiz> {
  private final UserRepository userRepository;
  private final CompletionRepository completionRepository;

  public FetchAnswerFeedbackUseCase(
      QuizRepository repository, UserRepository userRepository, CompletionRepository completionRepository) {
    super(repository);
    this.userRepository = userRepository;
    this.completionRepository = completionRepository;
  }

  public AnswerPresentation execute(long id, List<Integer> answer, String name) throws QuizNotFoundException {
    var user = userRepository.findByEmail(name).get();
    var quiz = repository.findById(id);
    if (quiz.isEmpty())
      throw new QuizNotFoundException();
    var presentation = new AnswerPresentation();
    presentation.success = quiz.get().checkAnswer(answer);

    if (presentation.success) {
      presentation.feedback = "Congratulations, you're right!";

      var completion = new Completion();
      completion.setCompletedAt(LocalDateTime.now());
      completion.setUser(user);
      completion.setQuiz(quiz.get());
      completionRepository.save(completion);
    }
    else
      presentation.feedback = "Wrong answer! Please, try again.";

    return presentation;
  }

}
