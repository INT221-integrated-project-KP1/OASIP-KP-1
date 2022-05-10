package sit.int204.actionback.dtos;

import lombok.*;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.entities.EventCategoryDto;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventOverLabDTO{
    private Instant eventStartTime;
    private EventCategory eventCategory;
    private Integer eventDuration;
}
