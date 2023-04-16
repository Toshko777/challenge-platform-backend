package challenge.platform.backend.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    private Long id;

    private String username;
    @NotEmpty(message = "name cannot be empty")
    private String firstName;
    @NotEmpty(message = "name cannot be empty")
    private String lastName;
    @NotEmpty(message = "email cannot be empty")
    private String email;
    @Size(min = 8, message = "password must be at least 8 characters")
    private String password;

    private List<Long> badges;

    private LocalDate created;

}
