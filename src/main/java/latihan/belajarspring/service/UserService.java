package latihan.belajarspring.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import latihan.belajarspring.entity.User;
import latihan.belajarspring.model.*;
import latihan.belajarspring.repository.UserRepository;
import latihan.belajarspring.security.BCrypt;
import latihan.belajarspring.utils.RequestValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private RequestValidator requestValidator;


    @Transactional
    public void register(RegisterUserRequest request){
        requestValidator.<RegisterUserRequest>validate(request);

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
        requestValidator.<LoginUserRequest>validate(request);

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
    public void logout(User user){
        user.setToken(null);
        user.setTokenExpiredAt(null);

        userRepository.save(user);
    }

    @Transactional
    public UserResponse get(User user){
        UserResponse userResponse = new UserResponse(user.getUsername(),user.getName());
        userResponse.setName(user.getName());
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }

    @Transactional
    public void updateUser(User user, UpdateUserRequest request){
        requestValidator.<UpdateUserRequest>validate(request);

        if(!BCrypt.checkpw(request.getPassword(),user.getPassword())){
            user.setPassword(BCrypt.hashpw(request.getPassword(),BCrypt.gensalt()));
        }

        user.setName(request.getName());

        userRepository.save(user);

    }
}
