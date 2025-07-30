package lab.manager.backend.global.thirdparty.k8s.config;

import static io.kubernetes.client.openapi.Configuration.setDefaultApiClient;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import java.io.FileReader;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KubeClientConfig {

  @Value("${kube.configPath}")
  private String configPath;

  @Bean
  public ApiClient apiClient() throws IOException {
    String kubeConfigPath = System.getenv("HOME") + configPath;
    ApiClient client = ClientBuilder
        .kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath)))
        .build();
    setDefaultApiClient(client);
    return client;
  }

  @Bean
  public CoreV1Api coreV1Api(ApiClient apiClient) {
    return new CoreV1Api(apiClient);
  }

  @Bean
  public AppsV1Api appsV1Api(ApiClient apiClient) {
    return new AppsV1Api(apiClient);
  }
}
