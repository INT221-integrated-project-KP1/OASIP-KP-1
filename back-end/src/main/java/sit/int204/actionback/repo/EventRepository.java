package sit.int204.actionback.repo;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.actionback.entities.Event;
import java.time.Instant;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByEventCategoryId(Integer eventCategoryId, Pageable pageable);
    List<Event> findAllByEventStartTimeBefore(Instant now, Pageable pageable);
    List<Event> findAllByEventStartTimeAfter(Instant now, Pageable pageable);
    List<Event> findAllByEventStartTimeAfterAndEventCategoryId(Instant now, Integer eventCategoryId, Pageable pageable);
    List<Event> findAllByEventStartTimeBeforeAndEventCategoryId(Instant now, Integer eventCategoryId, Pageable pageable);
    List<Event> findAllByIdNot(Integer eventId, Pageable pageable);
    List<Event> findAllByEventStartTimeBetween(Instant d, Instant t, Pageable pageable);
    List<Event> findAllByEventCategoryIdAndEventStartTimeBetween(Integer eventCategoryid, Instant d, Instant t, Pageable pageable);
    List<Event> findAllByIdNotAndEventCategoryIdAndEventStartTimeBetween(Integer eventId, Integer eventCategoryid, Instant d, Instant t,  Pageable pageable);
}