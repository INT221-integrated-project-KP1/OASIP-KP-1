package sit.int204.actionback.repo;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import sit.int204.actionback.dtos.SimpleEventDTO;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.entities.User;

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

    Event findEventByAttachment(String attachment);

    //หาตาม lecturer
    @Query(value = "select e.* from event e join eventCategory ec on e.event_category_id = ec.event_category_id join event_category_owner eco on ec.event_category_id = eco.event_category_id where eco.user_id = :lecturer_id order by e.event_start_time desc", nativeQuery = true)
    List<Event> findAllEventByLecturerCategory(Integer lecturer_id);

    @Query(value = "select e.* from event e join eventCategory ec on e.event_category_id = ec.event_category_id join event_category_owner eco on ec.event_category_id = eco.event_category_id join myuser mu on eco.user_id = mu.id where mu.email = :lecturer_email order by e.event_start_time desc", nativeQuery = true)
    List<Event> findAllEventByLecturerEmail(String lecturer_email);
//    @Query(value = "select e.* from event e join event_category_owner eco on e.eventCategory = eco.eventCategory_id where eco.user_id = :lecturer_id and e.event_id = :event_id order by e.eventStartTime desc", nativeQuery = true)
//    Event findEventByLecturerCategory(Integer lecturer_id, Integer event_id);

    @Query(value = "update event set attachment = :eAttachment where booking_id = :eId",nativeQuery = true)
    @Transactional
    @Modifying
    void updateAttachment(@Param("eId") Integer id, @Param("eAttachment") String attachment);
}