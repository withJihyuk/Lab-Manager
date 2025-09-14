package lab.manager.backend.domain.template.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lab.manager.backend.domain.template.enums.SchemaValueType;
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
public class SchemaProperty {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  private UUID id;

  private String keyName;

  private String title;

  private SchemaValueType valueType;

  @Column(length = 1000)
  private String placeholder;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "platform_version_id")
  private PlatformVersion platformVersion;
}
