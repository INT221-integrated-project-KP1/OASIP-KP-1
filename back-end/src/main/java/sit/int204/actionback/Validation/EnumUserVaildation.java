package sit.int204.actionback.Validation;

import sit.int204.actionback.dtos.UserDTO;
import sit.int204.actionback.enumfile.Role;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnumUserVaildation implements ConstraintValidator<EnumUser, String > {


    @Override
    public void initialize(EnumUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String myRole, ConstraintValidatorContext constraintValidatorContext) {
        if(myRole == null) return true;

        Role roles[] = Role.values();
        for(Role role: roles) {
            System.out.println(role);
            if(role.toString().equals(myRole)) return true;
        }
        return false;
    }
}