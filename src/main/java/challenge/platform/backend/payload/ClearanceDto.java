package challenge.platform.backend.payload;

import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Value;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClearanceDto {

    private Long id;

    @NotEmpty(message = "name cannot be empty")
    private String clearance_position;

    
   
}