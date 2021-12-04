package recipes.gateways;

import org.springframework.data.repository.CrudRepository;
import recipes.entities.User;

import java.util.Optional;

public interface UserGateway extends CrudRepository<User, Integer> {

  Optional<User> findByEmail(String email);
}
