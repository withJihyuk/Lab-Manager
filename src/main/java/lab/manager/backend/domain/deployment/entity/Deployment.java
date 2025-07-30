package lab.manager.backend.domain.deployment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lab.manager.backend.domain.app.entity.App;
import lab.manager.backend.domain.deployment.enums.DeploymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Deployment {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  private UUID id;

  @ManyToOne
  private App app;

  @Enumerated(EnumType.STRING)
  private DeploymentStatus status;

  private String gitCommitHash;

  @CreationTimestamp
  private LocalDateTime createdAt;

  private LocalDateTime completedAt;
}
