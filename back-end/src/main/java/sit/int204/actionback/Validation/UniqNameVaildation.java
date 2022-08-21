package sit.int204.actionback.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import sit.int204.actionback.dtos.UserDTO;
import sit.int204.actionback.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqNameVaildation implements ConstraintValidator<UniqName, String > {

@Autowired
UserService userService;

    @Override
    public void initialize(UniqName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if(name == null) return true;
        List<UserDTO> allUser  = userService.getUserAll();
        for (int i = 0 ; i < allUser.size() ; i++){
            System.out.println(allUser.get(i).getName().toLowerCase().equals(name.toLowerCase()));
           if(allUser.get(i).getName().toLowerCase().equals(name.toLowerCase())) return false;
        }
        return true;
    }
}