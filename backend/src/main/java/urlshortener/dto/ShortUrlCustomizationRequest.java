package urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ShortenUrlWithAliasRequest {
    
    @NotBlank(message = "Original URL is required")
    private String originalUrl;
    
    @Pattern(regexp = "^[a-zA-Z0-9_-]*$", message = "Custom url can only contain letters, numbers, hyphens, and underscores")
    private String customAlias;
    
    private Integer expiresInDays;
}