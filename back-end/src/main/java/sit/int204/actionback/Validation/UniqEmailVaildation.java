package sit.int204.actionback.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import sit.int204.actionback.dtos.UserDTO;
import sit.int204.actionback.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqEmailVaildation implements ConstraintValidator<UniqEmail, UserDTO > {

    @Autowired
    UserService userService;

    @Override
    public void initialize(UniqEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDTO user, ConstraintValidatorContext constraintValidatorContext) {
        if(user.getEmail() == null )  return true;

        int myid = 0 ;
        if(user.getId() == null){myid = -1;}
        else {myid = user.getId();}

        List<UserDTO> allUser  = userService.getUserAll();
        for (int i = 0 ; i < allUser.size() ; i++){
            if(allUser.get(i).getId() != myid){
                if(allUser.get(i).getEmail().equals(user.getEmail())) return false;
            }

        }
        return true;
    }
}