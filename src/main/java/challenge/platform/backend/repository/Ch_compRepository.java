package challenge.platform.backend.repository;

import challenge.platform.backend.entity.Ch_comp;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ch_compRepository extends JpaRepository<Ch_comp, Long> {

    

}
