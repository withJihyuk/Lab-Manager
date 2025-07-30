package lab.manager.backend.domain.workspace.controller;

import java.util.List;
import lab.manager.backend.domain.workspace.dto.CreateWorkspaceRequest;
import lab.manager.backend.domain.workspace.dto.DeleteWorkspaceRequest;
import lab.manager.backend.domain.workspace.dto.ReadOwnWorkspacesResponse;
import lab.manager.backend.domain.workspace.usecase.CreateWorkspaceUsecase;
import lab.manager.backend.domain.workspace.usecase.DeleteWorkspaceUsecase;
import lab.manager.backend.domain.workspace.usecase.ReadOwnWorkspacesUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/workspace")
public class WorkspaceController {

  private final CreateWorkspaceUsecase createWorkspaceUsecase;
  private final DeleteWorkspaceUsecase deleteWorkspaceUsecase;
  private final ReadOwnWorkspacesUsecase readOwnWorkspacesUsecase;

  @PostMapping
  public ResponseEntity<Void> createWorkspace(
      @RequestBody CreateWorkspaceRequest request
  ) {
    createWorkspaceUsecase.execute(request);
    return ResponseEntity.ok().build();
  }

  @DeleteMapping
  public ResponseEntity<Void> deleteWorkspace(
      @RequestBody DeleteWorkspaceRequest request
  ) {
    deleteWorkspaceUsecase.execute(request);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<ReadOwnWorkspacesResponse>> getWorkspace(
  ) {
    List<ReadOwnWorkspacesResponse> response = readOwnWorkspacesUsecase.execute();
    return ResponseEntity.ok(response);
  }
}
