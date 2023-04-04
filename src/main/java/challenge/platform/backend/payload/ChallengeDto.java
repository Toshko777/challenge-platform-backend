package challenge.platform.backend.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChallengeDto {
    private Long id;
    @NotEmpty(message = "must enter challenge name")
    private String name;
    @NotEmpty(message = "must enter challenge description")
    private String description;
    private Long creatorId;
    private LocalDate created;
}
