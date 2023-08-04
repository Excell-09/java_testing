package latihan.belajarspring.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LogoutUserRequest {

    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(max=100)
    private String token;

}
