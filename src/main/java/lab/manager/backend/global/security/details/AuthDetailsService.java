package lab.manager.backend.global.security.details;

import java.util.UUID;
import lab.manager.backend.domain.user.entity.User;
import lab.manager.backend.domain.user.repository.UserRepository;
import lab.manager.backend.global.exception.HttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    UUID id = UUID.fromString(username);
    User userByEmail =
        userRepository.findById(id).orElseThrow(() ->
            new HttpException(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다.")
        );
    return new AuthDetails(userByEmail);
  }
}
