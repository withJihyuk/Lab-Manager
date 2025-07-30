package lab.manager.backend.domain.auth.entity;

import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("verify-code")
public class VerifyCode {

  @Id
  private String phoneNumber;

  private String code;

  @TimeToLive(unit = TimeUnit.MILLISECONDS)
  private Long expires;
}
