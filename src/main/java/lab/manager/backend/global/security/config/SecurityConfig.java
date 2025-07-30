package lab.manager.backend.global.security.config;

import lab.manager.backend.domain.user.enums.Role;
import lab.manager.backend.global.security.filter.JwtFilter;
import lab.manager.backend.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtProvider jwtProvider;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    JwtFilter jwtFilter = new JwtFilter(jwtProvider);

    return http
        .authorizeHttpRequests(it -> it
            // 인증
            .requestMatchers("/auth/**").permitAll()
            // 워크스페이스
            .requestMatchers("/workspace/**")
            .hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name())
            // 앱
            .requestMatchers("/app/**")
            .hasAnyAuthority(Role.ROLE_ADMIN.name(), Role.ROLE_USER.name())
            // 상태 확인
            .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
        )
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .httpBasic(AbstractHttpConfigurer::disable)
        .sessionManagement(it ->
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }
}
