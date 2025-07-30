package lab.manager.backend.domain.workspace.usecase;

import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import jakarta.transaction.Transactional;
import lab.manager.backend.domain.workspace.dto.CreateWorkspaceRequest;
import lab.manager.backend.domain.workspace.entity.Workspace;
import lab.manager.backend.domain.workspace.repository.WorkspaceRepository;
import lab.manager.backend.global.exception.HttpException;
import lab.manager.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateWorkspaceUsecase {

  private final WorkspaceRepository workspaceRepository;
  private final CoreV1Api coreV1Api;
  private final UserUtil userUtil;

  @Transactional
  public void execute(CreateWorkspaceRequest request) {
    Workspace result = workspaceRepository.save(Workspace.builder()
        .name(request.getName())
        .owner(userUtil.getUser())
        .build());
    V1Namespace v1Namespace = new V1Namespace();
    V1ObjectMeta metadata = new V1ObjectMeta();
    metadata.setName(result.getId().toString());
    v1Namespace.setMetadata(metadata);
    v1Namespace.setApiVersion("v1");
    v1Namespace.setKind("Namespace");

    try {
      coreV1Api.createNamespace(v1Namespace).execute();
    } catch (Exception e) {
      throw new HttpException(HttpStatus.BAD_GATEWAY, "클러스터에 문제가 발생 하였습니다.");
    }
  }
}
