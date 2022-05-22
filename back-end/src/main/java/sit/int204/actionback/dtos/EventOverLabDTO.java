package sit.int204.actionback.dtos;

import lombok.*;
import sit.int204.actionback.entities.EventCategory;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventOverLabDTO{
    private Instant eventStartTime;
    private Integer eventDuration;
}
