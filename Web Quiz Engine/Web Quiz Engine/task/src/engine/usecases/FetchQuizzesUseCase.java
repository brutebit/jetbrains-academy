package engine.usecases;

import engine.entities.Quiz;
import engine.repositories.QuizRepository;
import engine.presentations.QuizPresentation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FetchQuizzesUseCase extends UseCase<Quiz> {

  public FetchQuizzesUseCase(QuizRepository repository) {
    super(repository);
  }

  public Page<QuizPresentation> execute(int pageNo, int pageSize, String sortKey) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortKey));
    return ((QuizRepository) repository).findAll(paging).map(q -> {
      var presentation = new QuizPresentation();
      presentation.options = q.getOptions();
      presentation.title = q.getTitle();
      presentation.text = q.getText();
      presentation.id = q.getId();
      return presentation;
    });
  }
}
