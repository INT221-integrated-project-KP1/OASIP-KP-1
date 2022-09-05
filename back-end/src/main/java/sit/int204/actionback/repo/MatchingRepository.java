package sit.int204.actionback.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.actionback.entities.User;

import java.util.Optional;

public interface MatchingRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
