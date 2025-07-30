package lab.manager.backend.domain.auth.repository;

import java.util.Optional;
import lab.manager.backend.domain.auth.entity.VerifyCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerifyCountRepository extends JpaRepository<VerifyCount, Long> {

  Optional<VerifyCount> findByPhoneNumber(String phoneNumber);
}
