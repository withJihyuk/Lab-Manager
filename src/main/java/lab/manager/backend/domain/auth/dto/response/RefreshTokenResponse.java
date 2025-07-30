package lab.manager.backend.domain.auth.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenResponse {

  private String accessToken;
  private LocalDateTime accessTokenExpiredAt;
  private String refreshToken;
  private LocalDateTime refreshTokenExpiredAt;
}