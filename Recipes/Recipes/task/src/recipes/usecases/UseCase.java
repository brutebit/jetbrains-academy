package recipes.usecases;

import recipes.gateways.RecipeGateway;

public abstract class UseCase {
  protected RecipeGateway gateway;

  public UseCase(RecipeGateway gateway) {
    this.gateway = gateway;
  }
}
