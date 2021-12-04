package recipes.usecases;

import recipes.entities.Recipe;
import recipes.gateways.RecipeGateway;

public class FetchRecipeUseCase extends UseCase {
  private Recipe recipe;

  public FetchRecipeUseCase(RecipeGateway gateway) {
    super(gateway);
  }

  public void execute(int id) throws RecipeNotFoundException {
    var opt = gateway.findById(id);
    if (opt.isEmpty())
      throw new RecipeNotFoundException();
    this.recipe = opt.get();
  }

  public Recipe getRecipe() {
    return recipe;
  }
}
