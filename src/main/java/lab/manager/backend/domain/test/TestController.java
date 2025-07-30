package lab.manager.backend.domain.test;

import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

  private final CoreV1Api coreV1Api;

  @GetMapping("/namespaces")
  public void getAllNamespaces() throws Exception {
    String fieldSelector = "status.phase=Running";

    V1PodList podList = coreV1Api
        .listPodForAllNamespaces()
        .fieldSelector(fieldSelector)
        .execute();

    for (V1Pod pod : podList.getItems()) {
      System.out.println(pod.getMetadata().getName());
    }
  }
}
