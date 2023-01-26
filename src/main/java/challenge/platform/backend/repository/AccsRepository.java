package challenge.platform.backend.repository;

import challenge.platform.backend.entity.Accs;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccsRepository extends JpaRepository<Accs, Long> {

    

}
