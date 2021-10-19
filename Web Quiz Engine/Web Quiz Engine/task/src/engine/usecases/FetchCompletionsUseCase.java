package engine.usecases;

import engine.entities.Completion;
import engine.presentations.CompletionPresentation;
import engine.repositories.CompletionRepository;
import engine.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class FetchCompletionsUseCase extends UseCase<Completion> {
  private final UserRepository userRepository;

  public FetchCompletionsUseCase(CompletionRepository repository, UserRepository userRepository) {
    super(repository);
    this.userRepository = userRepository;
  }

  public Page<CompletionPresentation> execute(int page, int size, String sortKey, String name) {
    var user = userRepository.findByEmail(name).get();
    Pageable paging = PageRequest.of(page, size, Sort.by(sortKey).descending());
    return ((CompletionRepository) repository).findAllByUser(user, paging).map(c -> {
      var presentation = new CompletionPresentation();
      presentation.id = c.getQuiz().getId();
      presentation.completedAt = c.getCompletedAt();
      return presentation;
    });
  }
}
