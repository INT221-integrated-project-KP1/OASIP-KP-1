package sit.int204.actionback.repo;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.actionback.dtos.SimpleEventDTO;
import sit.int204.actionback.entities.Event;
import java.time.Instant;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
    List<Event> findAllByEventCategoryId(Integer eventCategoryId, Pageable pageable);
    List<Event> findAllByBookingEmailAndEventCategoryId(String email, Integer eventCategoryId, Pageable pageable);
    List<Event> findAllByEventStartTimeBefore(Instant now, Pageable pageable);

    List<Event> findAllByBookingEmailAndEventStartTimeBefore(String email, Instant now, Pageable pageable);

    List<Event> findAllByBookingEmailAndEventStartTimeAfter(String email, Instant now, Pageable pageable);
    List<Event> findAllByEventStartTimeAfter(Instant now, Pageable pageable);
    List<Event> findAllByEventStartTimeAfterAndEventCategoryId(Instant now, Integer eventCategoryId, Pageable pageable);
    List<Event> findAllByBookingEmailAndEventStartTimeAfterAndEventCategoryId(String email, Instant now, Integer eventCategoryId, Pageable pageable);
    List<Event> findAllByBookingEmailAndEventStartTimeBeforeAndEventCategoryId(String email, Instant now, Integer eventCategoryId, Pageable pageable);
    List<Event> findAllByEventStartTimeBeforeAndEventCategoryId(Instant now, Integer eventCategoryId, Pageable pageable);
    List<Event> findAllByBookingEmailAndIdNot(String email, Integer eventId, Pageable pageable);
    List<Event> findAllByIdNot(Integer eventId, Pageable pageable);
    List<Event> findAllByEventStartTimeBetween(Instant d, Instant t, Pageable pageable);
    List<Event> findAllByBookingEmailAndEventStartTimeBetween(String email, Instant d, Instant t, Pageable pageable);
    List<Event> findAllByEventCategoryIdAndEventStartTimeBetween(Integer eventCategoryid, Instant d, Instant t, Pageable pageable);
    List<Event> findAllByBookingEmailAndEventCategoryIdAndEventStartTimeBetween(String email, Integer eventCategoryid, Instant d, Instant t, Pageable pageable);
    List<Event> findAllByIdNotAndEventCategoryIdAndEventStartTimeBetween(Integer eventId, Integer eventCategoryid, Instant d, Instant t,  Pageable pageable);
    List<Event> findAllByBookingEmail(String bookingEmail, Pageable pageable);
    List<Event> findAllByBookingEmail(String bookingEmail , Sort sort);
    List<Event> findAll(Sort sort);
}