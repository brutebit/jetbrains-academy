package engine.usecases;

import engine.entities.Quiz;
import engine.repositories.QuizRepository;
import engine.presentations.QuizPresentation;

public class FetchQuizUseCase extends UseCase<Quiz> {
  public FetchQuizUseCase(QuizRepository repository) {
    super(repository);
  }

  public QuizPresentation execute(long id) throws QuizNotFoundException {
    var quiz = repository.findById(id);
    if (quiz.isEmpty())
      throw new QuizNotFoundException();
    var presentation = new QuizPresentation();
    presentation.id = id;
    presentation.options = quiz.get().getOptions();
    presentation.text = quiz.get().getText();
    presentation.title = quiz.get().getTitle();
    return presentation;
  }
}
