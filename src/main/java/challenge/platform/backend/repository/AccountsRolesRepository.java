package challenge.platform.backend.repository;

import challenge.platform.backend.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRolesRepository extends JpaRepository<AccountRole, Long> {

    void deleteByAccountId(Long accountId);
}
