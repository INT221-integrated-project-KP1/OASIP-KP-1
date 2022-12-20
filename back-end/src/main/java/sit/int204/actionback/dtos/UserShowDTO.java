package sit.int204.actionback.dtos;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserShowDTO {
    private Integer id;
    private String name;
    private String email;
}
