package lab.manager.backend.global.util;

import lab.manager.backend.domain.user.entity.User;
import lab.manager.backend.domain.user.repository.UserRepository;
import lab.manager.backend.global.exception.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

  private final UserRepository userRepository;

  public User getUser() {
    String phoneNumber = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(() ->
        new HttpException(HttpStatus.NOT_FOUND, "해당하는 회원을 찾을 수 없습니다.")
    );
  }
}
