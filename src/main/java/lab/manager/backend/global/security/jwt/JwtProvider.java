package lab.manager.backend.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import lab.manager.backend.domain.auth.entity.RefreshToken;
import lab.manager.backend.domain.auth.repository.RefreshTokenRepository;
import lab.manager.backend.global.exception.HttpException;
import lab.manager.backend.global.security.details.AuthDetailsService;
import lab.manager.backend.global.security.jwt.dto.JwtDetails;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

  private final AuthDetailsService authDetailsService;
  private final RefreshTokenRepository refreshTokenRepository;
  private final String accessTokenKey;
  private final String refreshTokenKey;
  private final long accessTokenExpires;
  @Getter
  private final long refreshTokenExpires;

  public JwtProvider(
      AuthDetailsService authDetailsService,
      RefreshTokenRepository refreshTokenRepository,
      @Value("${jwt.access-token-key}") String accessTokenKey,
      @Value("${jwt.refresh-token-key}") String refreshTokenKey,
      @Value("${jwt.access-token-expires}") long accessTokenExpires,
      @Value("${jwt.refresh-token-expires}") long refreshTokenExpires) {
    this.authDetailsService = authDetailsService;
    this.refreshTokenRepository = refreshTokenRepository;
    this.accessTokenKey = accessTokenKey;
    this.refreshTokenKey = refreshTokenKey;
    this.accessTokenExpires = accessTokenExpires;
    this.refreshTokenExpires = refreshTokenExpires;
  }

  public UsernamePasswordAuthenticationToken getAuthentication(String token) {
    String resolvedToken = resolveToken(token);
    Claims payload = getPayload(resolvedToken, JwtType.ACCESS_TOKEN);

    var userDetails = authDetailsService.loadUserByUsername(payload.getSubject());

    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  public String resolveToken(String token) {
    if (token == null || !token.startsWith("Bearer ")) {
      return null;
    } else {
      return token.substring(7);
    }
  }

  public RefreshToken getSavedRefreshTokenByRefreshToken(String refreshToken) {
    UUID userId = UUID.fromString(getPayload(refreshToken, JwtType.REFRESH_TOKEN).getSubject());
    return refreshTokenRepository.findById(userId).orElseThrow(() ->
        new HttpException(HttpStatus.NOT_FOUND, "리프레시 토큰을 찾을 수 없습니다.")
    );
  }

  public String getIdByRefreshToken(String refreshToken) {
    return getPayload(refreshToken, JwtType.REFRESH_TOKEN).getSubject();
  }

  public Claims getPayload(String token, JwtType jwtType) {
    if (token == null) {
      throw new HttpException(HttpStatus.FORBIDDEN, "토큰이 비어있습니다.");
    }

    String tokenKey = jwtType == JwtType.ACCESS_TOKEN ? accessTokenKey : refreshTokenKey;
    byte[] keyBytes = Base64.getEncoder().encode(tokenKey.getBytes());
    var signingKey = Keys.hmacShaKeyFor(keyBytes);

    try {
      return Jwts
          .parser()
          .verifyWith(signingKey)
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (ExpiredJwtException e) {
      throw new HttpException(HttpStatus.FORBIDDEN, "만료된 토큰입니다.");
    } catch (UnsupportedJwtException e) {
      throw new HttpException(HttpStatus.FORBIDDEN, "지원하지 않는 형식의 토큰입니다.");
    } catch (MalformedJwtException e) {
      throw new HttpException(HttpStatus.FORBIDDEN, "올바르지 않은 토큰입니다.");
    } catch (RuntimeException e) {
      throw new HttpException(HttpStatus.FORBIDDEN, "JWT 인증 과정에서 문제가 생겼습니다.");
    }
  }

  public JwtDetails generateToken(String id, JwtType jwtType) {
    boolean isAccessToken = jwtType == JwtType.ACCESS_TOKEN;
    LocalDateTime expiredAt = LocalDateTime.now().plus(
        Duration.ofMillis(isAccessToken ? accessTokenExpires : refreshTokenExpires)
    );

    String tokenKey = jwtType == JwtType.ACCESS_TOKEN ? accessTokenKey : refreshTokenKey;
    byte[] keyBytes = Base64.getEncoder().encode(tokenKey.getBytes());
    var signingKey = Keys.hmacShaKeyFor(keyBytes);

    Date expiration = Date.from(expiredAt.atZone(ZoneId.of("Asia/Seoul")).toInstant());

    String token = Jwts.builder()
        .subject(id)
        .signWith(signingKey)
        .issuedAt(new Date())
        .expiration(expiration)
        .compact();

    return new JwtDetails(token, expiredAt);
  }

}
