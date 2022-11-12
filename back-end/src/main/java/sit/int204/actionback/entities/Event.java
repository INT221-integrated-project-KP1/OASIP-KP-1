package sit.int204.actionback.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.Instant;

@Entity
@Getter
@Setter
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

    @Column(name = "attachment")
    private String attachment;

}