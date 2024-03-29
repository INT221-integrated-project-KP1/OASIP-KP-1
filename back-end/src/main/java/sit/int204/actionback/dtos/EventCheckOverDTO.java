package sit.int204.actionback.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventCheckOverDTO {
    private Instant eventStartTime;
    private Integer eventDuration;
}
