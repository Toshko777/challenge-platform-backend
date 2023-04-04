package challenge.platform.backend.repository;


import challenge.platform.backend.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountChallengeRepository extends JpaRepository<Challenge, Long> {

    

}
