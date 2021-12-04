package recipes.usecases;

import recipes.gateways.RecipeGateway;

public class DeleteRecipeUseCase extends UseCase {
  public DeleteRecipeUseCase(RecipeGateway gateway) {
    super(gateway);
  }

  public void execute(int id, String userEmail) throws RecipeNotFoundException, ForbiddenOperationException {
    var opt = gateway.findById(id);
    if (opt.isEmpty())
      throw new RecipeNotFoundException();

    if (!opt.get().getAuthor().getEmail().equals(userEmail))
      throw new ForbiddenOperationException();

    gateway.delete(opt.get());
  }
}
