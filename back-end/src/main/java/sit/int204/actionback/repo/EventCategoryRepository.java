package sit.int204.actionback.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.entities.EventCategory;

import java.util.List;

public interface EventCategoryRepository extends JpaRepository<EventCategory, Integer> {
    EventCategory findByEventCategoryNameIgnoreCase(String name);
    EventCategory findAllById(Integer id);
    String findEventCategoryNameById(Integer id);

    EventCategory findEventCategoryById(Integer id);

    @Query(value = "select ec.* from eventCategory ec join event_category_owner eco on ec.event_category_id = eco.event_category_id join myuser mu on eco.user_id = mu.id where mu.email = :lecturer_email", nativeQuery = true)
    List<EventCategory> findAllEventCategoryByLecturerEmail(String lecturer_email);

}