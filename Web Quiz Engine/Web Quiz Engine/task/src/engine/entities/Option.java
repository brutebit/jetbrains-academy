package engine.entities;

import javax.persistence.*;

@Entity
public class Option {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String body;

  @Column
  private boolean isAnswer;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public boolean isAnswer() {
    return isAnswer;
  }

  public void setAnswer(boolean answer) {
    isAnswer = answer;
  }
}
