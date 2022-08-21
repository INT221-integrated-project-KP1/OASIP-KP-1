package sit.int204.actionback.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import sit.int204.actionback.dtos.UserDTO;
import sit.int204.actionback.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqEmailVaildation implements ConstraintValidator<UniqEmail, String > {

@Autowired
UserService userService;

    @Override
    public void initialize(UniqEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(email == null) return true;
        List<UserDTO> allUser  = userService.getUserAll();
        for (int i = 0 ; i < allUser.size() ; i++){
            System.out.println(allUser.get(i).getEmail().equals(email));
            if(allUser.get(i).getEmail().equals(email)) return false;
        }
        return true;
    }
}