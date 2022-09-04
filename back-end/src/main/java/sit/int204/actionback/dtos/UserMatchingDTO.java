package sit.int204.actionback.dtos;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Data
public class UserMatchingDTO implements Serializable {

    @Email(message = "is not email")
    @NotNull(message = "is Not Null")
    @Size(min=1 ,max=50)
    private String email;
    @NotNull(message = "is Not Null")
    @Size(min=8 , max=18)
    private String password;
}