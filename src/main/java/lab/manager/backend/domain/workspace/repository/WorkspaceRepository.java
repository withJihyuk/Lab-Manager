package lab.manager.backend.domain.workspace.repository;

import java.util.List;
import java.util.UUID;
import lab.manager.backend.domain.workspace.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, UUID> {

  List<Workspace> findAllByOwnerId(UUID ownerId);
}
