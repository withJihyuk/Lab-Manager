package lab.manager.backend.domain.auth.scheduler;

import lab.manager.backend.domain.auth.repository.VerifyCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthScheduler {

  private final VerifyCountRepository verifyCountRepository;

  @Scheduled(cron = "0 0 0 * * *")
  public void resetVerifyCount() {
    verifyCountRepository.deleteAll();
  }
}
