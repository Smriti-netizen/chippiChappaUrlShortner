package urlshortener.service;

import urlshortener.entity.UrlMapping;
import urlshortener.repository.UrlMappingRepository;
import urlshortener.exception.AliasAlreadyExistsException;
import urlshortener.exception.InvalidUrlException;
import urlshortener.exception.UrlNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UrlShortenerService {
    
    private final UrlMappingRepository repository;
    private final ShortCodeGenerator codeGenerator;
    
    @Value("${app.base-url:http://localhost:8080}")
    private String baseUrl;
    
    public UrlShortenerService(UrlMappingRepository repository, ShortCodeGenerator codeGenerator) {
        this.repository = repository;
        this.codeGenerator = codeGenerator;
    }
    
    @Transactional
    public UrlMapping shortenUrl(String originalUrl) {
        validateUrl(originalUrl);
        
        String shortCode = generateUniqueShortCode();
        
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(originalUrl);
        mapping.setShortCode(shortCode);
        mapping.setCreatedAt(LocalDateTime.now());
        mapping.setClickCount(0L);
        mapping.setIsActive(true);
        
        return repository.save(mapping);
    }
    
    @Transactional
    public UrlMapping shortenUrlWithAlias(String originalUrl, String customAlias) {
        validateUrl(originalUrl);
        validateCustomAlias(customAlias);
        
        if (repository.existsByCustomAlias(customAlias) || repository.existsByShortCode(customAlias)) {
            throw new AliasAlreadyExistsException("Custom Url Name '" + customAlias + "' already exists");
        }
        
        UrlMapping mapping = new UrlMapping();
        mapping.setOriginalUrl(originalUrl);
        mapping.setShortCode(customAlias);
        mapping.setCustomAlias(customAlias);
        mapping.setCreatedAt(LocalDateTime.now());
        mapping.setClickCount(0L);
        mapping.setIsActive(true);
        
        return repository.save(mapping);
    }
    
    public UrlMapping getOriginalUrl(String shortCode) {
        Optional<UrlMapping> mapping = repository.findByShortCode(shortCode);
        
        if (mapping.isEmpty() || !mapping.get().getIsActive()) {
            throw new UrlNotFoundException("Short URL not found: " + shortCode);
        }
        
        UrlMapping urlMapping = mapping.get();
        urlMapping.setClickCount(urlMapping.getClickCount() + 1);
        repository.save(urlMapping);
        
        return urlMapping;
    }
    
    private String generateUniqueShortCode() {
        String shortCode;
        int attempts = 0;
        do {
            shortCode = codeGenerator.generateShortCode();
            attempts++;
            if (attempts > 10) {
                // If too many attempts, add more randomness
                shortCode = codeGenerator.generateShortCode(8);
            }
        } while (repository.existsByShortCode(shortCode));
        
        return shortCode;
    }
    
    private void validateUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new InvalidUrlException("URL cannot be empty");
        }
        
        try {
            URL urlObj = new URL(url);
            String protocol = urlObj.getProtocol();
            if (!protocol.equals("http") && !protocol.equals("https")) {
                throw new InvalidUrlException("URL must start with http:// or https://");
            }
        } catch (Exception e) {
            throw new InvalidUrlException("Invalid URL format: " + url);
        }
    }
    
    private void validateCustomAlias(String alias) {
        if (alias == null || alias.trim().isEmpty()) {
            throw new InvalidUrlException("Custom Url cannot be empty");
        }
        
        if (alias.length() < 3 || alias.length() > 50) {
            throw new InvalidUrlException("Custom alias must be between 3 and 50 characters");
        }
        
        if (!alias.matches("^[a-zA-Z0-9_-]+$")) {
            throw new InvalidUrlException("Custom Url can only contain letters, numbers, hyphens, and underscores");
        }
        
        // Reserved words check
        String[] reservedWords = {"api", "admin", "login", "logout", "register", "h2-console", "chippichappa"};
        for (String reserved : reservedWords) {
            if (alias.equalsIgnoreCase(reserved)) {
                throw new InvalidUrlException("Custom Url '" + alias + "' is reserved");
            }
        }
    }
    
    public String getShortUrl(String shortCode) {
        return baseUrl + "/" + shortCode;
    }
}