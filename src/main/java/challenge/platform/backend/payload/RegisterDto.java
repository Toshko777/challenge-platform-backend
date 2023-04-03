package challenge.platform.backend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDto {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
