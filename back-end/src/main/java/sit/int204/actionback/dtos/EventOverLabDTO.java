package sit.int204.actionback.dtos;

import lombok.*;

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
