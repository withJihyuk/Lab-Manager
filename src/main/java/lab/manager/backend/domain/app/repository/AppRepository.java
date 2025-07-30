package lab.manager.backend.domain.app.repository;

import java.util.UUID;
import lab.manager.backend.domain.app.entity.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRepository extends JpaRepository<App, UUID> {

}
