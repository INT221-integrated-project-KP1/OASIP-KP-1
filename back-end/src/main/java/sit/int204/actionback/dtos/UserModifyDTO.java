package sit.int204.actionback.dtos;

import lombok.Data;
import sit.int204.actionback.Validation.EnumUser;
import sit.int204.actionback.Validation.UniqEmailForModifyUser;
import sit.int204.actionback.Validation.UniqNameForModifyUser;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@UniqEmailForModifyUser
@UniqNameForModifyUser
public class UserModifyDTO implements Serializable {

    private final Integer id;


    @Size(min=1 , max=100)
    @NotNull(message = "is Not Null")
    private final String name;


    @Email(message = "is not email")
    @NotNull(message = "is Not Null")
    @Size(min=1 ,max=50)
    private final String email;

    @EnumUser
    @NotNull(message = "is Not Null")
    private final String role;
}
