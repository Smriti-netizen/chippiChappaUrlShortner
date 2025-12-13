package urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrlResponse {
    
    private String shortCode;
    private String shortUrl;
    private String originalUrl;
    private String customAlias;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}