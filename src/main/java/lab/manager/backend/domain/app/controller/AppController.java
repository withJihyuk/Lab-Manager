package lab.manager.backend.domain.app.controller;

import lab.manager.backend.domain.app.dto.CreateAppRequest;
import lab.manager.backend.domain.app.usecase.CreateAppUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppController {

  private final CreateAppUsecase createAppUsecase;

  @PostMapping
  public ResponseEntity<Void> createApp(
      @RequestBody CreateAppRequest request
  ) {
    createAppUsecase.execute(request);
    return ResponseEntity.ok().build();
  }

}
