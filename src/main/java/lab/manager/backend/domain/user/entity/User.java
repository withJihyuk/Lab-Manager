package lab.manager.backend.domain.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;
import lab.manager.backend.domain.user.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

  @Id
  @UuidGenerator
  @GeneratedValue
  private UUID id;

  @Column(unique = true, nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private String encodedPassword;

  @Enumerated(EnumType.STRING)
  private Role role;
}
