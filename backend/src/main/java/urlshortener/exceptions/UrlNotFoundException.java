package urlshortener.exception;

public class UrlNotFoundException extends BaseException {
    
    public UrlNotFoundException(String message) {
        super(message);
    }
}