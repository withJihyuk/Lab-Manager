package lab.manager.backend.domain.auth.usecase;

import lab.manager.backend.domain.auth.dto.request.JoinRequest;
import lab.manager.backend.domain.auth.entity.VerifyCode;
import lab.manager.backend.domain.auth.repository.VerifyCodeRepository;
import lab.manager.backend.domain.user.entity.User;
import lab.manager.backend.domain.user.enums.Role;
import lab.manager.backend.domain.user.repository.UserRepository;
import lab.manager.backend.global.exception.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JoinUsecase {

  private final UserRepository userRepository;
  private final VerifyCodeRepository verifyCodeRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void execute(JoinRequest request) {
    String phoneNumber = request.getPhoneNumber();

    if (userRepository.existsByPhoneNumber(phoneNumber)) {
      throw new HttpException(HttpStatus.BAD_REQUEST, "이미 등록된 전화번호입니다.");
    }

    String encodedPassword = passwordEncoder.encode(request.getPassword());

    VerifyCode verifyCode = verifyCodeRepository.findByPhoneNumber(phoneNumber)
        .orElseThrow(() ->
            new HttpException(HttpStatus.BAD_REQUEST, "전송된 인증번호를 찾을 수 없습니다.")
        );

    if (!request.getCode().equals(verifyCode.getCode())) {
      throw new HttpException(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다.");
    }

    verifyCodeRepository.delete(verifyCode);

    User user = User.builder()
        .phoneNumber(request.getPhoneNumber())
        .encodedPassword(encodedPassword)
        .role(Role.ROLE_USER)
        .build();

    userRepository.save(user);
  }
}
