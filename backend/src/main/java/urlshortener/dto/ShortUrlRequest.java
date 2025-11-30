package urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShortenUrlRequest {
    
    @NotBlank(message = "URL is required")
    private String originalUrl;
    
    private Integer expiresInDays;
}