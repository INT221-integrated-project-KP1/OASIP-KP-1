package sit.int204.actionback.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.Size;
import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventUpdateDTO {
    @Future
    private Instant eventStartTime;

    @Size(max=500)
    private String eventNotes;


}
