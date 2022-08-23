package sit.int204.actionback.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserMatchingDTO implements Serializable {

    @Email(message = "is not email")
    @NotNull(message = "is Not Null")
    @Size(min=1 ,max=50)
    private final String email;
    @NotNull(message = "is Not Null")
    private final String password;
}
