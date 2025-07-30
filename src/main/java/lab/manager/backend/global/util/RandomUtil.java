package lab.manager.backend.global.util;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomUtil {

  public String generateRandomCode(int length) {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    Random random = new Random();
    StringBuilder code = new StringBuilder();

    for (int i = 0; i < length; i++) {
      code.append(characters.charAt(random.nextInt(characters.length())));
    }

    return code.toString();
  }
}
