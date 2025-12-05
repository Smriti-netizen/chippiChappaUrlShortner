package urlshortener.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ShortCodeGenerator {

    private static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int DEFAULT_LENGTH = 6;
    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 10;
    
    private final Random random = new Random();
    
    /**
     * Generates a short code with the default length (6 characters)
     */
    public String generateShortCode() {
        return generateShortCode(DEFAULT_LENGTH);
    }
    
    /**
     * Generates a short code with a custom length (clamped between 4-10 characters)
     * @param length desired length of the short code
     * @return short code prefixed with "chippichappa/"
     */
    public String generateShortCode(int length) {
        // Clamp length to valid range
        int validLength = Math.max(MIN_LENGTH, Math.min(MAX_LENGTH, length));
        
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < validLength; i++) {
            code.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        }
        return "chippichappa/" + code.toString();
    }
}



