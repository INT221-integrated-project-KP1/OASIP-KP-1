package sit.int204.actionback.dtos;

import sit.int204.actionback.entities.EventCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//ตอน Get จาก page
public class SimpleEventDTO {
    private Integer Id;
    private Instant eventStartTime;
    private Integer eventDuration;
    private EventCategory eventCategory;
    private String bookingName;
    private String bookingEmail;
    private String eventNotes;
}
