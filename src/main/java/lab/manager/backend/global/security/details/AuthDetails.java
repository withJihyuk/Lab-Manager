package lab.manager.backend.global.security.details;

import java.util.Collection;
import java.util.List;
import lab.manager.backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class AuthDetails implements UserDetails {

  private final User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(
        new SimpleGrantedAuthority(user.getRole().name())
    );
  }

  @Override
  public String getPassword() {
    return user.getEncodedPassword();
  }

  @Override
  public String getUsername() {
    return user.getPhoneNumber();
  }
}
