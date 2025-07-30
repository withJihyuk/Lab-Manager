package lab.manager.backend.domain.runtimeConfig.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
public class RuntimeConfig {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  private UUID id;

  @OneToOne
  private App app;

  private Float cpu;

  private Integer memory;

  private Boolean autoStop;
}
