package latihan.belajarspring.controller;


import latihan.belajarspring.model.RegisterUserRequest;
import latihan.belajarspring.model.WebResponse;
import latihan.belajarspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(
            path = "/api/users",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> register(@RequestBody RegisterUserRequest request){
         userService.register(request);

         WebResponse<String> response = new WebResponse<>();
         response.setData("OK");

         return response;
    }
}
