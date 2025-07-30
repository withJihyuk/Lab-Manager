package lab.manager.backend.domain.auth.controller;

import jakarta.validation.Valid;
import lab.manager.backend.domain.auth.dto.request.JoinRequest;
import lab.manager.backend.domain.auth.dto.request.LoginRequest;
import lab.manager.backend.domain.auth.dto.response.LoginResponse;
import lab.manager.backend.domain.auth.dto.response.RefreshTokenResponse;
import lab.manager.backend.domain.auth.usecase.JoinUsecase;
import lab.manager.backend.domain.auth.usecase.LoginUsecase;
import lab.manager.backend.domain.auth.usecase.LogoutUsecase;
import lab.manager.backend.domain.auth.usecase.RefreshTokenUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final JoinUsecase joinUsecase;
  private final LoginUsecase loginUsecase;
  private final RefreshTokenUsecase refreshTokenUsecase;
  private final LogoutUsecase logoutUsecase;

//	@PostMapping("/verify")
//	public ResponseEntity<Void> sendVerifyCode(
//		@Valid @RequestBody SendVerifyCodeRequest sendVerifyCodeRequest
//	){
//		sendVerifyCodeUsecase.execute(sendVerifyCodeRequest);
//		return ResponseEntity.ok().build();
//	}

  @PostMapping("/join")
  public ResponseEntity<Void> joinUser(
      @Valid @RequestBody JoinRequest joinRequest
  ) {
    joinUsecase.execute(joinRequest);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
      @Valid @RequestBody LoginRequest loginRequest
  ) {
    LoginResponse response = loginUsecase.execute(loginRequest);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/logout")
  public ResponseEntity<Void> logout(
      @RequestHeader("Refresh-Token") String refreshToken
  ) {
    String resolvedToken = refreshToken.substring(7);
    logoutUsecase.execute(resolvedToken);
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/refresh")
  public ResponseEntity<RefreshTokenResponse> refreshToken(
      @RequestHeader("Refresh-Token") String refreshToken
  ) {
    String resolvedToken = refreshToken.substring(7);
    RefreshTokenResponse response = refreshTokenUsecase.execute(resolvedToken);
    return ResponseEntity.ok(response);
  }
}
