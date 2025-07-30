package lab.manager.backend.domain.auth.usecase;

import lab.manager.backend.domain.auth.entity.RefreshToken;
import lab.manager.backend.domain.auth.repository.RefreshTokenRepository;
import lab.manager.backend.global.exception.HttpException;
import lab.manager.backend.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LogoutUsecase {

  private final RefreshTokenRepository refreshTokenRepository;
  private final JwtProvider jwtProvider;

  public void execute(String resolveRefreshToken) {
    RefreshToken savedRefreshToken = jwtProvider.getSavedRefreshTokenByRefreshToken(
        resolveRefreshToken);

    if (!resolveRefreshToken.equals(savedRefreshToken.getRefreshToken())) {
      throw new HttpException(HttpStatus.FORBIDDEN, "올바르지 않은 리프레시 토큰입니다.");
    }

    refreshTokenRepository.delete(savedRefreshToken);
  }
}
