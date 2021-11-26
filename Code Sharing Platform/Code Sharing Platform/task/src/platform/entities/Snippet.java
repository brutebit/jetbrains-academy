package platform.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
public class Snippet {

  @Id
  private String id;

  @Column
  private String code;

  @Column
  private LocalDateTime loadDate;

  @Column
  private int restrictTime;

  @Column
  private int restrictViews;

  @Column
  private int numberOfViews;

  @Column
  private boolean isRestricted;

  public void setRestricted(boolean restricted) {
    isRestricted = restricted;
  }

  public boolean isRestricted() {
    return isRestricted;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public LocalDateTime getLoadDate() {
    return loadDate;
  }

  public void setLoadDate(LocalDateTime loadDate) {
    this.loadDate = loadDate;
  }

  public String getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id.toString();
  }

  public int getRestrictTime() {
    return restrictTime;
  }

  public void setRestrictTime(int restrictTime) {
    this.restrictTime = restrictTime;
    this.isRestricted = isViewRestricted() || isTimeRestricted();
  }

  public int getRestrictViews() {
    return restrictViews;
  }

  public void setRestrictViews(int restrictViews) {
    this.restrictViews = restrictViews;
    this.isRestricted = isViewRestricted() || isTimeRestricted();
  }

  public int getNumberOfViews() {
    return numberOfViews;
  }

  public void setNumberOfViews(int numberOfViews) {
    this.numberOfViews = numberOfViews;
  }

  public boolean isTimeRestricted() {
    return restrictTime > 0;
  }

  public boolean isViewRestricted() {
    return restrictViews > 0;
  }

  public boolean isDisplayable() {
    if (!isRestricted())
      return true;

    if (isTimeRestricted())
      return timeRemaining() > 0;

    return numberOfViews < restrictViews;
  }

  public int timeRemaining() {
    if (!isTimeRestricted())
      return 0;
    return (int) LocalDateTime.now().until(loadDate.plusSeconds(restrictTime), ChronoUnit.SECONDS);
  }

  public int viewsRemaining() {
    if (!isViewRestricted())
      return 0;
    return restrictViews - numberOfViews;
  }

  public void incrementViews() {
    numberOfViews++;
  }
}
