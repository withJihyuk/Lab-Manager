package lab.manager.backend.domain.app.dto;

import java.util.List;
import java.util.UUID;
import lab.manager.backend.domain.app.entity.App;
import lab.manager.backend.domain.app.entity.AppPort;
import lab.manager.backend.domain.app.enums.AppStatus;
import lab.manager.backend.domain.workspace.entity.Workspace;

public record CreateAppRequest(
    UUID workspaceId,
    String name,
    String repositoryUrl,
    String branch,
    String buildCommand,
    String runCommand,
    List<Integer> port
) {

  public App toEntity(Workspace workspace) {
    App app = App.builder()
        .name(name)
        .repositoryUrl(repositoryUrl)
        .branch(branch)
        .buildCommand(buildCommand)
        .runCommand(runCommand)
        .status(AppStatus.STOPPED)
        .workspace(workspace)
        .build();

    List<AppPort> appPorts = port.stream()
        .map(p -> AppPort.builder()
            .port(p)
            .app(app)
            .build())
        .toList();

    app.getPorts().addAll(appPorts);

    return app;
  }

}
