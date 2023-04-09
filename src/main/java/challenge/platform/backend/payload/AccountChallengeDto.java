package challenge.platform.backend.payload;




import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountChallengeDto {
    private Long id;

//    @NotEmpty(message = "accountId cannot be empty")
    private Long accountId;
    
//    @NotEmpty(message = "challengeId cannot be empty")
    private Long challengeId;


    private LocalDate started;
    private LocalDate completed;
}
