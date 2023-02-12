package challenge.platform.backend.repository;

import challenge.platform.backend.entity.AccountRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgesRepository extends JpaRepository<AccountRole, Long> {

    

}
