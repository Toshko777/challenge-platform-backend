package challenge.platform.backend.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty(message = "name cannot be empty")
    private String first_name;

    @NotEmpty(message = "name cannot be empty")
    private String fam_name;

    
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;

    @NotEmpty(message = "email cannot be empty")
    private String email;

    private Long id_challengge_compl;
}
