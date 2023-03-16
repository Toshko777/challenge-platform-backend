package challenge.platform.backend.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRoleDto {

    private Long id;

    @NotEmpty(message = "name cannot be empty")
    private String username;


    private String role;


}