package sit.int204.actionback.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.actionback.entities.User;

public interface MatchingRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
}
