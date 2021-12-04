package recipes.usecases;

import recipes.entities.Recipe;
import recipes.gateways.RecipeGateway;

import java.util.List;

public class FetchRecipesByNameUseCase extends UseCase{
  List<Recipe> recipes;

  public FetchRecipesByNameUseCase(RecipeGateway gateway) {
    super(gateway);
  }

  public void execute(String name) {
    this.recipes = gateway.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
  }

  public List<Recipe> getRecipes() {
    return recipes;
  }
}
