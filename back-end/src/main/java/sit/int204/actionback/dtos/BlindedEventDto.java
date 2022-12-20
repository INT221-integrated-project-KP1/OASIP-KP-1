package sit.int204.actionback.dtos;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link sit.int204.actionback.entities.Event} entity
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlindedEventDto  {
    private Integer id;
    private Instant eventStartTime;

    private Integer eventCategoryId;
    private String eventCategoryEventCategoryName;

    @Min(1)
    @Max(480)
    private Integer eventDuration;
}