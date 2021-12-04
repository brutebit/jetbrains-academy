package recipes.gateways;

import org.springframework.data.repository.CrudRepository;
import recipes.entities.Recipe;

import java.util.List;

public interface RecipeGateway extends CrudRepository<Recipe, Integer> {
  List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);
  List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
