package recipes.usecases;

import recipes.entities.Recipe;
import recipes.gateways.RecipeGateway;
import recipes.gateways.UserGateway;

public class InsertRecipeUseCase extends UseCase {
  private final UserGateway userGateway;
  private int id;

  public InsertRecipeUseCase(RecipeGateway gateway, UserGateway userGateway) {
    super(gateway);
    this.userGateway = userGateway;
  }

  public void execute(Recipe recipe, String userEmail) {
    var user = userGateway.findByEmail(userEmail).get();
    recipe.setAuthor(user);
    gateway.save(recipe);
    this.id = recipe.getId();
  }

  public int getId() {
    return id;
  }
}
