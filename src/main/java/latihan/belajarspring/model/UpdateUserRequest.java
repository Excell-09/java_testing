package latihan.belajarspring.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UpdateUserRequest {

    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String password;
}
