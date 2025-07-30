package lab.manager.backend.domain.buildLog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lab.manager.backend.domain.deployment.entity.Deployment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildLog {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  private UUID id;

  @ManyToOne
  private Deployment deployment;

  private String logText;

  private LocalDateTime timestamp;
}
