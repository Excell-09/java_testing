package latihan.belajarspring.controller;


import latihan.belajarspring.entity.User;
import latihan.belajarspring.model.*;
import latihan.belajarspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(
            path = "/api/users/logout",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> logout(@RequestBody LogoutUserRequest request){
        userService.logout(request);
        WebResponse<String> response = new WebResponse<>();
        response.setData("User Logout");
        return response;
    }


    @GetMapping(
            path = "/api/users/current",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<User> getUser(@RequestBody GetUserRequest request){
        User user = userService.getUser(request);
        WebResponse<User> response = new WebResponse<>();
        response.setData(user);
        return response;
    }
}
