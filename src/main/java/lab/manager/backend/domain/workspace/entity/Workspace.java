package lab.manager.backend.domain.workspace.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lab.manager.backend.domain.user.entity.User;
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
public class Workspace {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  private UUID id;

  private String name;

  @ManyToOne
  private User owner;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
