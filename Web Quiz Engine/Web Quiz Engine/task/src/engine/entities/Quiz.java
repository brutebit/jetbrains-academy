package engine.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String title;

  @Column
  private String text;

  @Column
  @OneToMany(cascade = CascadeType.ALL)
  private List<Option> optionList;

  @ManyToOne(cascade = CascadeType.PERSIST)
  private User user;

  @Column
  @OneToMany(cascade = CascadeType.ALL)
  private List<Completion> completions;

  public Quiz() {
  }

  public boolean checkAnswer(List<Integer> answer) {
    if (answer.size() != getAnswerIndexes().size())
      return false;

    for (Integer a : answer)
      if (!getAnswerIndexes().contains(a))
        return false;
    return true;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setOptionList(List<Option> optionList) {
    this.optionList = optionList;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public List<String> getOptions() {
    var ops = new ArrayList<String>();
    for (var op : optionList)
      ops.add(op.getBody());
    return ops;
  }

  public List<Integer> getAnswerIndexes() {
    var ops = new ArrayList<Integer>();
    for (var op : optionList)
      if (op.isAnswer())
        ops.add(optionList.indexOf(op));
    return ops;
  }

  public List<Completion> getCompletions() {
    return completions;
  }

  public void setCompletions(List<Completion> completions) {
    this.completions = completions;
  }
}
