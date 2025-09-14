package lab.manager.backend.domain.workspace.usecase;

import io.kubernetes.client.openapi.apis.CoreV1Api;
import jakarta.transaction.Transactional;
import lab.manager.backend.domain.workspace.dto.DeleteWorkspaceRequest;
import lab.manager.backend.domain.workspace.entity.Workspace;
import lab.manager.backend.domain.workspace.repository.WorkspaceRepository;
import lab.manager.backend.global.exception.HttpException;
import lab.manager.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteWorkspaceUsecase {

  private final WorkspaceRepository workspaceRepository;
  private final CoreV1Api coreV1Api;
  private final UserUtil userUtil;

  @Transactional
  public void execute(DeleteWorkspaceRequest request) {
    Workspace result = workspaceRepository.findById(request.getId()).orElseThrow(
        () -> new HttpException(HttpStatus.NOT_FOUND, "워크스페이스가 존재하지 않습니다.")
    );

    if (result.getOwner() != userUtil.getUser()) {
      throw new HttpException(HttpStatus.FORBIDDEN, "본인의 워크스페이스가 아닙니다.");
    }
    
    workspaceRepository.delete(result);
  }
}
