package challenge.platform.backend.repository;

import challenge.platform.backend.entity.CompletedChallenge;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ch_compRepository extends JpaRepository<CompletedChallenge, Long> {

    

}
