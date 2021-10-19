package engine.usecases;

import org.springframework.data.repository.CrudRepository;

public abstract class UseCase<T> {
  protected CrudRepository<T, Long> repository;

  public UseCase(CrudRepository<T, Long> repository) {
    this.repository = repository;
  }

  public static class QuizNotFoundException extends Exception {
  }
}
