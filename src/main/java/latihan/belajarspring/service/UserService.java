package latihan.belajarspring.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import latihan.belajarspring.entity.User;
import latihan.belajarspring.model.GetUserRequest;
import latihan.belajarspring.model.LoginUserRequest;
import latihan.belajarspring.model.LogoutUserRequest;
import latihan.belajarspring.model.RegisterUserRequest;
import latihan.belajarspring.repository.UserRepository;
import latihan.belajarspring.security.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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

    @Transactional
    public User login(LoginUserRequest request){
        Set<ConstraintViolation<LoginUserRequest>> constraintViolations = validator.validate(request);

        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"username not found!"));


        if(!BCrypt.checkpw(request.getPassword(),user.getPassword())){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"password wrong!");
        }

        user.setToken(UUID.randomUUID().toString());
        user.setTokenExpiredAt(System.currentTimeMillis() + (1000*60*24*30));

        userRepository.save(user);

        return user;
    }

    @Transactional
    public void logout(LogoutUserRequest request){
        Set<ConstraintViolation<LogoutUserRequest>> constraintViolations = validator.validate(request);

        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"username not found!"));

        if(user.getToken() == null){
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Token");
        }

        user.setToken(null);
        user.setTokenExpiredAt(null);

        userRepository.save(user);
    }

    @Transactional
    public User getUser(GetUserRequest request){
        Set<ConstraintViolation<GetUserRequest>> constraintViolations = validator.validate(request);

        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }

        User user = userRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED,"username not found!"));

        if(user.getToken() == null){
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Token");
        }

        if(!user.getToken().equals(request.getToken())){
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Token");
        }

        if(user.getTokenExpiredAt() == 0){
            user.setToken(null);
            user.setTokenExpiredAt(null);
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Token Expired");
        }

        user.setToken(UUID.randomUUID().toString());

        userRepository.save(user);

        return  user;
    }
}
