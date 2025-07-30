package lab.manager.backend.domain.workspace.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadOwnWorkspacesRequest {

  @NotBlank
  private UUID id;
}
