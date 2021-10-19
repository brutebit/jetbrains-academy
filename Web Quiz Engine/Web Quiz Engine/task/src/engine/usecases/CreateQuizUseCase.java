package engine.usecases;

import engine.entities.Option;
import engine.entities.Quiz;
import engine.presentations.QuizPresentation;
import engine.repositories.QuizRepository;
import engine.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class CreateQuizUseCase extends UseCase<Quiz> {
  private String title, text;
  private List<String> options;
  private List<Integer> answerIndexes;
  private final UserRepository userRepository;

  public CreateQuizUseCase(QuizRepository repository, UserRepository userRepository) {
    super(repository);
    this.userRepository = userRepository;
  }

  public QuizPresentation execute(String name) {
    var user = userRepository.findByEmail(name).get();
    var quiz = new Quiz();
    quiz.setText(text);
    quiz.setTitle(title);
    quiz.setUser(user);
    var optionList = new ArrayList<Option>();
    for (var op : options) {
      var option = new Option();
      option.setBody(op);
      if (answerIndexes.contains(options.indexOf(op)))
        option.setAnswer(true);
      optionList.add(option);
    }
    quiz.setOptionList(optionList);

    var presentation = new QuizPresentation();
    presentation.id = repository.save(quiz).getId();
    presentation.title = title;
    presentation.text = text;
    presentation.options = options;
    return presentation;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setText(String text) {
    this.text = text;
  }

  public void setOptions(List<String> options) {
    this.options = options;
  }

  public void setAnswerIndexes(List<Integer> answerIndex) {
    this.answerIndexes = answerIndex;
  }
}
