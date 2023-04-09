package challenge.platform.backend.repository;

import challenge.platform.backend.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BadgesRepository extends JpaRepository<Badge, Long> {

    Badge findByName(String name);
}
