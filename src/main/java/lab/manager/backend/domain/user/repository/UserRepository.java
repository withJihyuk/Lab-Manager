package lab.manager.backend.domain.user.repository;

import java.util.Optional;
import java.util.UUID;
import lab.manager.backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByPhoneNumber(String phoneNumber);

  Boolean existsByPhoneNumber(String phoneNumber);
}
