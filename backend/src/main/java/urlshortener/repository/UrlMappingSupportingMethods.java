package urlshortener.repository;

import urlshortener.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
//data access file

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    
    Optional<UrlMapping> findByShortCode(String shortCode);
    
    Optional<UrlMapping> findByCustomAlias(String customAlias);
    
    boolean existsByShortCode(String shortCode);
    
    boolean existsByCustomAlias(String customAlias);
}