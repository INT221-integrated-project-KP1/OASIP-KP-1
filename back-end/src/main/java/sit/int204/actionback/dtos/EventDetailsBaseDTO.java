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

public class EventDetailsBaseDTO {
    private Instant eventStartTime;
    private Integer eventDuration;
    private String bookingName;
    private String bookingEmail;
    private Integer id;
    private EventCategory eventCategory;
    private String eventNotes;
    private String attachment;
}
