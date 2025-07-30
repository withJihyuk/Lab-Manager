package lab.manager.backend.domain.auth.repository;

import java.util.UUID;
import lab.manager.backend.domain.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, UUID> {

  void deleteById(UUID id);
}
