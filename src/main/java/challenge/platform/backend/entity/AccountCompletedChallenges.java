package challenge.platform.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_completed_challenges")
public class AccountCompletedChallenges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId;

//    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL)
    public List<Long> completedChallenges;

}
