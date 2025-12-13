package urlshortener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.entity.UrlMapping;
import urlshortener.service.UrlShortenerService;

@RestController
public class RedirectController {
    
    private final UrlShortenerService urlShortenerService;
    
    public RedirectController(UrlShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }
    
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        UrlMapping mapping = urlShortenerService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND)
            .header("Location", mapping.getOriginalUrl())
            .build();
    }
}
