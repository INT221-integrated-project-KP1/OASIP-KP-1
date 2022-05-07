package sit.int204.actionback.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int204.actionback.entities.EventCategory;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private String bookingName;
    private String bookingEmail;
    private String eventNotes;
    private Instant eventStartTime;
    private EventCategory eventCategory;
    private Integer eventDuration;
}