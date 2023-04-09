package challenge.platform.backend.payload;




import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountChallengeDto {
    private Long id;

    @NotNull(message = "accountId cannot be null")
    private Long accountId;
    
    @NotNull(message = "challengeId cannot be null")
    private Long challengeId;


    private LocalDate started;
    private LocalDate completed;
}
