package urlshortener.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.dto.ShortUrlRequest;
import urlshortener.dto.ShortUrlResponse;
import urlshortener.dto.ShortUrlCustomizationRequest;
import urlshortener.entity.UrlMapping;
import urlshortener.service.UrlShortenerService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5173", "https://yourusername.github.io"})
public class UrlShortenerController {
    
    private final UrlShortenerService urlShortenerService;
    
    public UrlShortenerController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }
    
    @PostMapping("/shorten")
    public ResponseEntity<ShortUrlResponse> shortenUrl(@Valid @RequestBody ShortUrlRequest request) {
        UrlMapping mapping = urlShortenerService.shortenUrl(request.getOriginalUrl());
        
        ShortUrlResponse response = new ShortUrlResponse(
            mapping.getShortCode(),
            urlShortenerService.getShortUrl(mapping.getShortCode()),
            mapping.getOriginalUrl(),
            mapping.getCustomAlias(),
            mapping.getCreatedAt(),
            mapping.getExpiresAt()
        );
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PostMapping("/shorten/custom")
    public ResponseEntity<ShortUrlResponse> shortenUrlWithAlias(
            @Valid @RequestBody ShortUrlCustomizationRequest request) {
        
        UrlMapping mapping;
        
        if (request.getCustomAlias() != null && !request.getCustomAlias().trim().isEmpty()) {
            mapping = urlShortenerService.shortenUrlWithAlias(
                request.getOriginalUrl(), 
                request.getCustomAlias()
            );
        } else {
            mapping = urlShortenerService.shortenUrl(request.getOriginalUrl());
        }
        
        ShortUrlResponse response = new ShortUrlResponse(
            mapping.getShortCode(),
            urlShortenerService.getShortUrl(mapping.getShortCode()),
            mapping.getOriginalUrl(),
            mapping.getCustomAlias(),
            mapping.getCreatedAt(),
            mapping.getExpiresAt()
        );
        
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

