package lab.manager.backend.domain.auth.usecase;

import java.util.UUID;
import lab.manager.backend.domain.auth.dto.response.RefreshTokenResponse;
import lab.manager.backend.domain.auth.entity.RefreshToken;
import lab.manager.backend.domain.auth.repository.RefreshTokenRepository;
import lab.manager.backend.global.exception.HttpException;
import lab.manager.backend.global.security.jwt.JwtProvider;
import lab.manager.backend.global.security.jwt.JwtType;
import lab.manager.backend.global.security.jwt.dto.JwtDetails;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RefreshTokenUsecase {

  private final JwtProvider jwtProvider;
  private final RefreshTokenRepository refreshTokenRepository;

  public RefreshTokenUsecase(JwtProvider jwtProvider,
      RefreshTokenRepository refreshTokenRepository) {
    this.jwtProvider = jwtProvider;
    this.refreshTokenRepository = refreshTokenRepository;
  }

  public RefreshTokenResponse execute(String resolveRefreshToken) {
    String currentUserId = jwtProvider.getIdByRefreshToken(resolveRefreshToken);
    RefreshToken savedRefreshToken = jwtProvider.getSavedRefreshTokenByRefreshToken(
        resolveRefreshToken);

    if (!resolveRefreshToken.equals(savedRefreshToken.getRefreshToken())) {
      throw new HttpException(HttpStatus.FORBIDDEN, "올바르지 않은 리프레시 토큰입니다.");
    }

    JwtDetails newAccessToken = jwtProvider.generateToken(currentUserId, JwtType.ACCESS_TOKEN);
    JwtDetails newRefreshToken = deleteRefreshTokenOrSave(currentUserId);

    RefreshToken newRefreshTokenEntity = new RefreshToken(
        UUID.fromString(currentUserId),
        newRefreshToken.getToken(),
        jwtProvider.getRefreshTokenExpires()
    );

    refreshTokenRepository.save(newRefreshTokenEntity);

    return new RefreshTokenResponse(
        newAccessToken.getToken(),
        newAccessToken.getExpiredAt(),
        newRefreshToken.getToken(),
        newRefreshToken.getExpiredAt()
    );
  }

  public JwtDetails deleteRefreshTokenOrSave(String id) {
    UUID uuid = UUID.fromString(id);
    RefreshToken refreshToken = refreshTokenRepository.findById(uuid).orElseThrow(() ->
        new HttpException(HttpStatus.NOT_FOUND, "리프레시 토큰을 찾을 수 없습니다."));

    refreshTokenRepository.delete(refreshToken);

    JwtDetails newRefreshToken = jwtProvider.generateToken(id, JwtType.REFRESH_TOKEN);

    RefreshToken newRefreshTokenEntity = new RefreshToken(
        uuid,
        newRefreshToken.getToken(),
        jwtProvider.getRefreshTokenExpires()
    );

    refreshTokenRepository.save(newRefreshTokenEntity);

    return newRefreshToken;
  }
}
