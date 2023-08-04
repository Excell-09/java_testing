package latihan.belajarspring.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GetUserRequest {

    @NotBlank
    @Size(max = 100)
    private  String username;

    @NotBlank
    private String token;

}
