package recipes.usecases;

import recipes.entities.Recipe;
import recipes.gateways.RecipeGateway;

import java.time.LocalDateTime;

public class UpdateRecipeUseCase extends UseCase {
  public UpdateRecipeUseCase(RecipeGateway gateway) {
    super(gateway);
  }

  public void execute(int id, Recipe recipe, String userEmail) throws RecipeNotFoundException, ForbiddenOperationException {
    var opt = gateway.findById(id);
    if (opt.isEmpty())
      throw new RecipeNotFoundException();

    if (!opt.get().getAuthor().getEmail().equals(userEmail))
      throw new ForbiddenOperationException();

    var r = opt.get();
    r.setName(recipe.getName());
    r.setCategory(recipe.getCategory());
    r.setDescription(recipe.getDescription());
    r.setDirections(recipe.getDirections());
    r.setIngredients(recipe.getIngredients());
    r.setDate(LocalDateTime.now());
    gateway.save(r);
  }
}
