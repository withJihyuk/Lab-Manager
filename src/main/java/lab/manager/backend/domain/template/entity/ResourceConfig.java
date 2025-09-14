package lab.manager.backend.domain.template.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lab.manager.backend.domain.template.enums.ResourceType;
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
public class ResourceConfig {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  private UUID id;

  private ResourceType resourceType;

  private Double minValue;

  private Double initialValue;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "platform_version_id")
  private PlatformVersion platformVersion;
}
