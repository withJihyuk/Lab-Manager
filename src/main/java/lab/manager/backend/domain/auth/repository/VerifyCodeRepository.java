package lab.manager.backend.domain.auth.repository;

import java.util.Optional;
import lab.manager.backend.domain.auth.entity.VerifyCode;
import org.springframework.data.repository.CrudRepository;

public interface VerifyCodeRepository extends CrudRepository<VerifyCode, String> {

  Optional<VerifyCode> findByPhoneNumber(String phoneNumber);
}
