package challenge.platform.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_completed_challenges")
public class AccountCompletedChallenges {

    @Id
    private Long accountId;
    private Long challengeId;
}
