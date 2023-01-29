package challenge.platform.backend.repository;


import challenge.platform.backend.entity.Clearance;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClearanceRepository extends JpaRepository<Clearance, Long> {

    

}