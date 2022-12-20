package sit.int204.actionback.dtos;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int204.actionback.Validation.Overlab;
import sit.int204.actionback.entities.EventCategory;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Overlab
public class EventDTO {
    @NotNull
    @Size(min=1,max=100)
    private String bookingName;

    @NotNull
    @Email(message = "is not email")
    private String bookingEmail;

    @Size(min = 0 , max = 500)
    private String eventNotes;

    @Future
    private Instant eventStartTime;

    private EventCategory eventCategory;

    private String attachment;

}