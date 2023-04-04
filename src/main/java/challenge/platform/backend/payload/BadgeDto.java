package challenge.platform.backend.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadgeDto {
    
    private Long id;
    @NotEmpty(message = "name cannot be empty")
    private String name;
    @NotEmpty(message = "description cannot be empty")
    private String description;
    @NotEmpty(message = "condition cannot be empty")
    private String condition;
}
