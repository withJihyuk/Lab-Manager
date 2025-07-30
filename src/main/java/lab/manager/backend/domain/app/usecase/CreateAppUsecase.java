package lab.manager.backend.domain.app.usecase;

import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodSpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lab.manager.backend.domain.app.dto.CreateAppRequest;
import lab.manager.backend.domain.app.entity.App;
import lab.manager.backend.domain.app.entity.AppPort;
import lab.manager.backend.domain.app.enums.AppStatus;
import lab.manager.backend.domain.app.repository.AppRepository;
import lab.manager.backend.domain.workspace.entity.Workspace;
import lab.manager.backend.domain.workspace.repository.WorkspaceRepository;
import lab.manager.backend.global.exception.HttpException;
import lab.manager.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class CreateAppUsecase {

  private final AppRepository appRepository;
  private final WorkspaceRepository workspaceRepository;
  private final CoreV1Api coreV1Api;
  private final UserUtil userUtil;

  @Transactional
  public void execute(CreateAppRequest request) {
    Workspace workspace = workspaceRepository.findById(request.workspaceId())
        .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "워크스페이스가 존재하지 않습니다."));

    if (!workspace.getOwner().equals(userUtil.getUser())) {
      throw new HttpException(HttpStatus.FORBIDDEN, "본인의 워크스페이스가 아닙니다.");
    }

    App app = App.builder()
        .name(request.name())
        .repositoryUrl(request.repositoryUrl())
        .branch(request.branch())
        .buildCommand(request.buildCommand())
        .runCommand(request.runCommand())
        .status(AppStatus.STOPPED)
        .ports(new ArrayList<>())
        .workspace(workspace)
        .build();

    List<AppPort> ports = request.port().stream()
        .map(p -> AppPort.builder()
            .port(p)
            .app(app)
            .build())
        .toList();

    app.getPorts().addAll(ports);

    appRepository.save(app);

    V1Pod pod = new V1Pod()
        .metadata(new V1ObjectMeta()
            .name(app.getId().toString())
            .namespace(workspace.getId().toString())
            .labels(Collections.singletonMap("app", app.getName()))
        )
        .spec(new V1PodSpec()
            .overhead(null)
            .containers(Collections.singletonList(new V1Container()
                .name(app.getName())
                .image("your-builder-image")
                .args(Collections.singletonList("/bin/sh -c \"" + request.runCommand() + "\""))

            ))
        );

    try {
      coreV1Api.createNamespacedPod(
          workspace.getId().toString(), pod
      ).execute();
    } catch (Exception e) {
      throw new HttpException(HttpStatus.BAD_GATEWAY, "생성 중 오류가 발생했습니다.");
    }
  }
}