package lab.manager.backend.domain.workspace.usecase;

import java.util.List;
import java.util.stream.Collectors;
import lab.manager.backend.domain.workspace.dto.ReadOwnWorkspacesResponse;
import lab.manager.backend.domain.workspace.entity.Workspace;
import lab.manager.backend.domain.workspace.repository.WorkspaceRepository;
import lab.manager.backend.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReadOwnWorkspacesUsecase {

  private final WorkspaceRepository workspaceRepository;
  private final UserUtil userUtil;

  public List<ReadOwnWorkspacesResponse> execute() {
    List<Workspace> result = workspaceRepository.findAllByOwnerId(userUtil.getUser().getId());

    return result.stream()
        .map(ReadOwnWorkspacesResponse::from)
        .collect(Collectors.toList());

  }
}
