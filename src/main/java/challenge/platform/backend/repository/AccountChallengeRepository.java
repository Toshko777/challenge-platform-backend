package challenge.platform.backend.repository;


import challenge.platform.backend.entity.AccountChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountChallengeRepository extends JpaRepository<AccountChallenge, Long> {

    List<AccountChallenge> findAllChallengesByAccountId(long accountId);

    Optional<AccountChallenge> findByAccountIdAndChallengeId(long accountId, long challengeId);
}
