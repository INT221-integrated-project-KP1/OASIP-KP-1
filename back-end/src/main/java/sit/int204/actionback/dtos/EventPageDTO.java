package sit.int204.actionback.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
//Page ตอน Get
public class EventPageDTO {
    private List<SimpleEventDTO> content;
    private int number;
    private int size;
    private int totalPages;
    private int numberOfElements;
    private int totalElements;
    private boolean last;
    private boolean first;
}
