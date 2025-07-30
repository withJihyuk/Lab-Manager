package lab.manager.backend.domain.auth.entity;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("refresh-token")
public class RefreshToken {

  @Id
  private UUID id;

  private String refreshToken;

  @TimeToLive(unit = TimeUnit.MILLISECONDS)
  private Long expires;
}
