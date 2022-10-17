package sit.int204.actionback.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.actionback.entities.EventCategoryOwner;

public interface EventCategoryOwnerRepository extends JpaRepository<EventCategoryOwner, Integer> {
}