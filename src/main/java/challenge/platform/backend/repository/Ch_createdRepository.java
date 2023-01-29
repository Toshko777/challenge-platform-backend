package challenge.platform.backend.repository;


import challenge.platform.backend.entity.Ch_created;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ch_createdRepository extends JpaRepository<Ch_created, Long> {

    

}
