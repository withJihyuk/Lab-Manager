package lab.manager.backend.domain.app.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lab.manager.backend.domain.app.enums.AppStatus;
import lab.manager.backend.domain.envVar.entity.EnvVar;
import lab.manager.backend.domain.workspace.entity.Workspace;
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
public class App {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  private UUID id;

  private String name;

  private String repositoryUrl;

  private String branch;

  private String buildCommand;

  private String runCommand;

  @OneToMany(mappedBy = "app", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<EnvVar> envVars = new ArrayList<>();

  @OneToMany(mappedBy = "app", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AppPort> ports = new ArrayList<>();

  @Enumerated(EnumType.STRING)
  private AppStatus status = AppStatus.STOPPED;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToOne
  private Workspace workspace;
}
