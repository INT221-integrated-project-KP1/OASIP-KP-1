package sit.int204.actionback.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.service.EventCategoryService;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
    //EventCategory findByEventCategoryName(String name);
    EventCategory findByEventCategoryNameIgnoreCase(String name);
    EventCategory findAllById(Integer id);
    String findEventCategoryNameById(Integer id);

    EventCategory findEventCategoryById(Integer id);
}