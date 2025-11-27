//holds data fileds of data stored in h2 db 
package urlshortener.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "url_mappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlMapping {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 2048)
    private String originalUrl;
    
    @Column(unique = true, nullable = false, length = 100)
    private String shortCode;
    
    @Column(unique = true, nullable = true, length = 100)
    private String customAlias;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = true)
    private LocalDateTime expiresAt;
    
    @Column(nullable = false)
    private Long clickCount = 0L;
    
    @Column(nullable = false)
    private Boolean isActive = true;
}