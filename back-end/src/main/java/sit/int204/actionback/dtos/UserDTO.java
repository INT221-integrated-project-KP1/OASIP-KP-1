package sit.int204.actionback.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int204.actionback.Validation.EnumUser;
import sit.int204.actionback.Validation.UniqEmail;
import sit.int204.actionback.Validation.UniqName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@UniqName
@UniqEmail
public class UserDTO {
    private Integer id;
    @Size(min=1 , max=100)
    @NotNull(message = "is Not Null")
    private String name;
    @Email(message = "is not email")
    @NotNull(message = "is Not Null")
    @Size(min=1 ,max=50)
    private String email;
    @EnumUser
    @NotNull(message = "is Not Null")
    private String role;

    @NotNull(message = "is Not Null")
    private String password;
}