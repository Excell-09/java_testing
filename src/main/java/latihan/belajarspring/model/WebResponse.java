package latihan.belajarspring.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse <T> {

    private T data;
    private String errors;
}

// @Builder