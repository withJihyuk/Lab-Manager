package lab.manager.backend.domain.template.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedBy;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformVersion {

  @Id
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  private UUID id;

  @Column(nullable = false)
  private String version;

  private String location;

  private Boolean active;

  private Boolean maintenance;

  private String strategy;

  private String iconSrc;

  @CreatedBy
  private LocalDateTime created;

  @UpdateTimestamp
  private LocalDateTime updated;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "platform_id")
  private Platform platform;

  @OneToMany(mappedBy = "platformVersion", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ResourceConfig> resources;

  @OneToMany(mappedBy = "platformVersion", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SchemaProperty> schema;
}
