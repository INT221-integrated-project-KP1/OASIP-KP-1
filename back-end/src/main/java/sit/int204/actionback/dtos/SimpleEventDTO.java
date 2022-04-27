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
public class SimpleEventDTO {
    private Instant eventStartTime;
    private Integer eventDuration;
    private String eventCategoryName;
    private String bookingName;
    private Integer id;

}
