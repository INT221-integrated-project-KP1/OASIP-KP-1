package sit.int204.actionback.entities;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventCategoryDto implements Serializable {
    private final Integer id;
    private final String eventCategoryName;
    private final String eventCategoryDescription;
    private final Integer eventDuration;
}
