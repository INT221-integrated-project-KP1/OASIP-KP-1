package sit.int204.actionback.dtos;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link sit.int204.actionback.entities.User} entity
 */
@Data
public class UserDto implements Serializable {
    private final Integer id;
    private final String name;
    private final String email;
    private final String password;
    private final String role;
    private final Instant createdOn;
    private final Instant updatedOn;
}