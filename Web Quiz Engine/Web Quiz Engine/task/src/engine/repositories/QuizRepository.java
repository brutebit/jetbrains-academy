package engine.repositories;

import engine.entities.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;

public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {

  @Transactional
  @Override
  void delete(Quiz entity);
}
