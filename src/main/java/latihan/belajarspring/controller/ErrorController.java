package latihan.belajarspring.controller;

import jakarta.validation.ConstraintViolationException;
import latihan.belajarspring.model.WebResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<String>> ConstraintViolationException(ConstraintViolationException exception){

        WebResponse<String> response = new WebResponse<>();
        response.setErrors(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<String>> ApiException(ResponseStatusException exception){

        WebResponse<String> response = new WebResponse<>();
        response.setErrors(exception.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
