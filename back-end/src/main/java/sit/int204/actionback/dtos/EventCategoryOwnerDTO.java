package sit.int204.actionback.dtos;

import lombok.*;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.entities.User;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class EventCategoryOwnerDTO {
    private int[] userId;
    private Integer eventCategoryId;
}
