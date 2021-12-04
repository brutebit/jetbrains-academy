package recipes.usecases;

import recipes.entities.Recipe;
import recipes.gateways.RecipeGateway;

import java.util.List;

public class FetchRecipesByCategoryUseCase extends UseCase {
  List<Recipe> recipes;

  public FetchRecipesByCategoryUseCase(RecipeGateway gateway) {
    super(gateway);
  }

  public void execute(String category) {
    this.recipes = gateway.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
  }

  public List<Recipe> getRecipes() {
    return recipes;
  }
}
