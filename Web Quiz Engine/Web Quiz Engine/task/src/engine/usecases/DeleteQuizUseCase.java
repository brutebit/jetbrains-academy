package engine.usecases;
import engine.entities.Quiz;
import engine.repositories.CompletionRepository;
import engine.repositories.QuizRepository;
import engine.repositories.UserRepository;

import javax.transaction.Transaction;

public class DeleteQuizUseCase extends UseCase<Quiz> {
  private final UserRepository userRepository;
  private final CompletionRepository completionRepository;

  public DeleteQuizUseCase(
      QuizRepository repository, UserRepository userRepository, CompletionRepository completionRepository) {
    super(repository);
    this.userRepository = userRepository;
    this.completionRepository = completionRepository;
  }

  public void execute(long id, String name) throws QuizNotFoundException, ForbiddenException {
    var user = userRepository.findByEmail(name).get();
    var quiz = repository.findById(id);
    if (quiz.isEmpty())
      throw new UseCase.QuizNotFoundException();
    if (!quiz.get().getUser().getEmail().equals(user.getEmail()))
      throw new ForbiddenException();

    completionRepository.deleteByQuiz(quiz.get());
    repository.delete(quiz.get());
  }

  public static class ForbiddenException extends Throwable {
  }
}
