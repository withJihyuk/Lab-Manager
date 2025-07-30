package lab.manager.backend.domain.envVar.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lab.manager.backend.domain.app.entity.App;
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
public class EnvVar {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  private UUID id;

  @ManyToOne
  private App app;

  private String key;

  private String value;

  private Boolean isEncrypted;
}
