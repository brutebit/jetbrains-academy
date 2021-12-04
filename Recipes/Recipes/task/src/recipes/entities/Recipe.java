package recipes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private int id;

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotBlank
  private String category;

  private LocalDateTime date = LocalDateTime.now();

  @NotEmpty
  private String[] ingredients;

  @NotEmpty
  private String[] directions;

  @Column
  @JsonIgnore
  private User author;

  public Recipe() {
  }

  public Recipe(String name, String description, String[] ingredients, String[] directions) {
    this.name = name;
    this.description = description;
    this.ingredients = ingredients;
    this.directions = directions;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String[] getIngredients() {
    return ingredients;
  }

  public void setIngredients(String[] ingredients) {
    this.ingredients = ingredients;
  }

  public String[] getDirections() {
    return directions;
  }

  public void setDirections(String[] directions) {
    this.directions = directions;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }
}
