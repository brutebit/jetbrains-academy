package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.entities.Recipe;
import recipes.gateways.RecipeGateway;
import recipes.gateways.UserGateway;
import recipes.usecases.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
public class RecipeController {

  @Autowired
  private RecipeGateway gateway;

  @Autowired
  private UserGateway userGateway;

  @GetMapping("/api/recipe/{id}")
  public ResponseEntity<Recipe> fetchRecipe(@PathVariable int id) {
    var useCase = new FetchRecipeUseCase(gateway);
    try {
      useCase.execute(id);
      return ResponseEntity.ok(useCase.getRecipe());
    } catch (RecipeNotFoundException e) {
      return ResponseEntity.status(404).build();
    }
  }

  @PostMapping("/api/recipe/new")
  public Object insertRecipe(@Valid @RequestBody Recipe body, Principal principal) {
    var useCase = new InsertRecipeUseCase(gateway, userGateway);
    useCase.execute(body, principal.getName());
    return new Object() {
      public final int id = useCase.getId();
    };
  }

  @DeleteMapping("/api/recipe/{id}")
  public ResponseEntity deleteRecipe(@PathVariable int id, Principal principal) {
    var useCase = new DeleteRecipeUseCase(gateway);
    try {
      useCase.execute(id, principal.getName());
      return ResponseEntity.status(204).build();
    } catch (RecipeNotFoundException e) {
      return ResponseEntity.status(404).build();
    } catch (ForbiddenOperationException e) {
      return ResponseEntity.status(403).build();
    }
  }

  @PutMapping("/api/recipe/{id}")
  public ResponseEntity updateRecipe(@PathVariable int id, @Valid @RequestBody Recipe body, Principal principal) {
    var useCase = new UpdateRecipeUseCase(gateway);
    try {
      useCase.execute(id, body, principal.getName());
      return ResponseEntity.status(204).build();
    } catch (RecipeNotFoundException e) {
      return ResponseEntity.status(404).build();
    } catch (ForbiddenOperationException e) {
      return ResponseEntity.status(403).build();
    }
  }

  @GetMapping("/api/recipe/search")
  public ResponseEntity<List<Recipe>> search(@RequestParam Optional<String> name, @RequestParam Optional<String> category) {
    if (name.isEmpty() && category.isEmpty())
      return ResponseEntity.status(400).build();

    if (name.isEmpty()) {
      var useCase = new FetchRecipesByCategoryUseCase(gateway);
      useCase.execute(category.get());
      return ResponseEntity.ok(useCase.getRecipes());
    } else  {
      var useCase = new FetchRecipesByNameUseCase(gateway);
      useCase.execute(name.get());
      return ResponseEntity.ok(useCase.getRecipes());
    }
  }
}
