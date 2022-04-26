package sit.int204.actionback.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.actionback.entities.EventCategory;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
}