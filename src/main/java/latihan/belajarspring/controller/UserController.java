package latihan.belajarspring.controller;


import latihan.belajarspring.entity.User;
import latihan.belajarspring.model.*;
import latihan.belajarspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/api/users/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterUserRequest request){
         userService.register(request);
         WebResponse<String> response = new WebResponse<>();
         response.setData("OK");
         return response;
    }


    @PostMapping(
            path = "/api/users/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<User> login(@RequestBody LoginUserRequest request){
        User user = userService.login(request);
        WebResponse<User> response = new WebResponse<>();
        response.setData(user);
        return response;
    }

    @GetMapping(
            path = "/api/users/current",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<UserResponse> get(User user){
        UserResponse userResponse = userService.get(user);
        WebResponse<UserResponse> response = new WebResponse<>();
        response.setData(userResponse);
        return response;
    }

    @DeleteMapping(
            path = "/api/users/logout",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> logout(User user){
        userService.logout(user);
        WebResponse<String> response = new WebResponse<>();
        response.setData("User Logout success");

        return response;
    }

    @PatchMapping(
            path = "/api/users/update",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> updateUser(User user ,@RequestBody UpdateUserRequest request){
        userService.updateUser(user,request);
        WebResponse<String> response = new WebResponse<>();
        response.setData("User updated");

        return response;
    }



}
