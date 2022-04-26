package sit.int204.actionback.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.actionback.entities.Event;

import java.math.BigDecimal;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    public List<Event> findAllByOrderByEventStartTimeDesc();
}