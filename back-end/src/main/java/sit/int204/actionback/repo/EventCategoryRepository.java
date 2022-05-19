package sit.int204.actionback.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.service.EventCategoryService;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
    String findByEventCategoryName(String name);
    EventCategory findById (int id);
}