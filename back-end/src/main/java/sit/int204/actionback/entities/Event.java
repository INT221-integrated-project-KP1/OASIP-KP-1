package sit.int204.actionback.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Instant;

@Entity
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id", nullable = false)
    private Integer id;

    @Column(name = "booking_name", length = 100)
    private String bookingName;

    @Column(name = "booking_email")
    private String bookingEmail;

    @Column(name = "event_notes", length = 500)
    private String eventNotes;

    @Column(name = "event_start_time")
    private Instant eventStartTime;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "event_category_id", nullable = false)
    private EventCategory eventCategory;

    @Column(name = "event_duration")
    @Min(1)
    @Max(480)
    private Integer eventDuration;

    public Integer getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(Integer eventDuration) {
        this.eventDuration = eventDuration;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public Instant getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(Instant eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public String getEventNotes() {
        return eventNotes;
    }

    public void setEventNotes(String eventNotes) {
        this.eventNotes = eventNotes;
    }

    public String getBookingEmail() {
        return bookingEmail;
    }

    public void setBookingEmail(String bookingEmail) {
        this.bookingEmail = bookingEmail;
    }

    public String getBookingName() {
        return bookingName;
    }

    public void setBookingName(String bookingName) {
        this.bookingName = bookingName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}