package lab.manager.backend.global.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "thirdparty.ncp")
public class NcpProperties {

  private String serviceId;
  private String accessKey;
  private String secretKey;
  private String phoneNumber;
}


