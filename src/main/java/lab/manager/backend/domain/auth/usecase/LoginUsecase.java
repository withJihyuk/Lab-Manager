package lab.manager.backend.domain.auth.usecase;

import java.util.UUID;
import lab.manager.backend.domain.auth.dto.request.LoginRequest;
import lab.manager.backend.domain.auth.dto.response.LoginResponse;
import lab.manager.backend.domain.auth.entity.RefreshToken;
import lab.manager.backend.domain.auth.repository.RefreshTokenRepository;
import lab.manager.backend.domain.user.entity.User;
import lab.manager.backend.domain.user.repository.UserRepository;
import lab.manager.backend.global.exception.HttpException;
import lab.manager.backend.global.security.jwt.JwtProvider;
import lab.manager.backend.global.security.jwt.JwtType;
import lab.manager.backend.global.security.jwt.dto.JwtDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginUsecase {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtProvider jwtProvider;
  private final RefreshTokenRepository refreshTokenRepository;

  public LoginResponse execute(LoginRequest loginRequest) {
    String phoneNumber = loginRequest.getPhoneNumber();
    User user = userRepository.findByPhoneNumber(phoneNumber)
        .orElseThrow(() -> new HttpException(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."));

    String rawPassword = loginRequest.getPassword();
    String encodedPassword = user.getEncodedPassword();

    if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
      throw new HttpException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
    }

    JwtDetails accessToken = jwtProvider.generateToken(user.getId().toString(),
        JwtType.ACCESS_TOKEN);
    JwtDetails refreshToken = initialRefreshToken(user.getId());

    return new LoginResponse(
        accessToken.getToken(),
        accessToken.getExpiredAt(),
        refreshToken.getToken(),
        refreshToken.getExpiredAt()
    );
  }

  public JwtDetails initialRefreshToken(UUID id) {
    refreshTokenRepository.deleteById(id);

    JwtDetails newRefreshToken = jwtProvider.generateToken(id.toString(), JwtType.REFRESH_TOKEN);
    RefreshToken tokenEntity = new RefreshToken(id, newRefreshToken.getToken(),
        jwtProvider.getRefreshTokenExpires());
    refreshTokenRepository.save(tokenEntity);

    return newRefreshToken;
  }
}
