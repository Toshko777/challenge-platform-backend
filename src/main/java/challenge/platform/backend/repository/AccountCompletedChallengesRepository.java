package challenge.platform.backend.repository;


import challenge.platform.backend.entity.AccountCompletedChallenges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountCompletedChallengesRepository extends JpaRepository<AccountCompletedChallenges, Long> {
    Optional<AccountCompletedChallenges> findByAccountId(Long accountId);
}
