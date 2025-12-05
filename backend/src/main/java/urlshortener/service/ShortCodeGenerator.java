package urlshortener.service;

import org.springframework.stereotype.Component;
import java.util.Random;

@Component
public class ShortCodeGenerator {
    
    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int RANDOM_LENGTH = 6;
    private final Random random = new Random();
    
    public String generateShortCode(int length) {
        int validLength = Math.max(MIN_LENGTH, Math.min(MAX_LENGTH, length));
        
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < validLength; i++) {
            code.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        }
        return "chippichappa/" + code.toString();
    }
}