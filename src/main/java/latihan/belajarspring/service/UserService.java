package latihan.belajarspring.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import latihan.belajarspring.entity.User;
import latihan.belajarspring.model.RegisterUserRequest;
import latihan.belajarspring.repository.UserRepository;
import latihan.belajarspring.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private Validator validator;


    @Transactional
    public void register(RegisterUserRequest request){
        Set<ConstraintViolation<RegisterUserRequest>> constraintViolations = validator.validate(request);

        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }

        if(userRepository.existsById(request.getUsername())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"user already exits");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setName(request.getName());
        user.setPassword(BCrypt.hashpw(request.getPassword(),BCrypt.gensalt()));

        userRepository.save(user);

    }
}
