package pro.sky.coursework3.auction.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LotExceptionHandler {
    @ExceptionHandler(LotNotFoundException.class)
    public ResponseEntity<?> notFound() {
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(LotNotStartedYetException.class)
    public ResponseEntity<?> badRequest() {
        return ResponseEntity.badRequest().build();
    }

}
