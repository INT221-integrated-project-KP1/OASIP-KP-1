package sit.int204.actionback.repo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
    EventCategory findByEventCategoryNameIgnoreCase(String name);
    EventCategory findAllById(Integer id);
    String findEventCategoryNameById(Integer id);

    EventCategory findEventCategoryById(Integer id);
}