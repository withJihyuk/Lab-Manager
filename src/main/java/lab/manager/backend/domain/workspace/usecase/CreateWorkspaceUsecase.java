package lab.manager.backend.domain.workspace.usecase;

import io.kubernetes.client.openapi.apis.CoreV1Api;
import jakarta.transaction.Transactional;
import lab.manager.backend.domain.workspace.dto.CreateWorkspaceRequest;
import lab.manager.backend.domain.workspace.entity.Workspace;
import lab.manager.backend.domain.workspace.repository.WorkspaceRepository;
import lab.manager.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateWorkspaceUsecase {

  private final WorkspaceRepository workspaceRepository;
  private final CoreV1Api coreV1Api;
  private final UserUtil userUtil;

  @Transactional
  public void execute(CreateWorkspaceRequest request) {
    workspaceRepository.save(Workspace.builder()
        .name(request.getName())
        .owner(userUtil.getUser())
        .build());
  }
}
