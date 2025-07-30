package lab.manager.backend.domain.workspace.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lab.manager.backend.domain.workspace.entity.Workspace;
import lombok.Builder;

@Builder
public record ReadOwnWorkspacesResponse(
    UUID id,
    String name,
    LocalDateTime createdAt
) {

  public static ReadOwnWorkspacesResponse from(Workspace workspace) {
    return ReadOwnWorkspacesResponse.builder()
        .id(workspace.getId())
        .name(workspace.getName())
        .createdAt(workspace.getCreatedAt())
        .build();
  }
}