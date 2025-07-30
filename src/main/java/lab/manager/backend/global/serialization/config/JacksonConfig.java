package lab.manager.backend.global.serialization.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

  @Bean
  public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
    return Jackson2ObjectMapperBuilder.json()
        .serializers(
            new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
        .serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
  }
}
