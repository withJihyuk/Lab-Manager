package lab.manager.backend.domain.auth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VerifyCount {

  @Id
  @Column(nullable = false)
  private String phoneNumber;

  @Column(nullable = false)
  private Integer count;

  public void incrementCount() {
    count++;
  }
}
