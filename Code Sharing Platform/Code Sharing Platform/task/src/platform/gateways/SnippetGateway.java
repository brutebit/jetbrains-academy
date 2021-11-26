package platform.gateways;

import org.springframework.data.repository.CrudRepository;
import platform.entities.Snippet;

import java.util.List;
import java.util.Optional;

public interface SnippetGateway extends CrudRepository<Snippet, Long> {
  List<Snippet> findTop10ByIsRestrictedOrderByLoadDateDesc(boolean isRestricted);

  Optional<Snippet> findById(String id);
}
