package latihan.belajarspring.utils;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import latihan.belajarspring.model.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RequestValidator {

    @Autowired
    private Validator validator;

    public <T> void validate(T request){
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(request);

        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }

    }
}
