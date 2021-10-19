package engine.repositories;

import engine.entities.Completion;
import engine.entities.Quiz;
import engine.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

public interface CompletionRepository extends PagingAndSortingRepository<Completion, Long> {
  Page<Completion> findAllByUser(User user, Pageable pageable);

  @Transactional
  void deleteByQuiz(Quiz quiz);
}
