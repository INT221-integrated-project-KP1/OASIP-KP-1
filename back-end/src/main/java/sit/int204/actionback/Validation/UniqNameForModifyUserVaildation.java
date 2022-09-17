package sit.int204.actionback.Validation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import sit.int204.actionback.dtos.UserModifyDTO;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.repo.UserRepository;
import sit.int204.actionback.service.UserService;
import sit.int204.actionback.utils.ListMapper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqNameForModifyUserVaildation implements ConstraintValidator<UniqNameForModifyUser, UserModifyDTO> {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void initialize(UniqNameForModifyUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserModifyDTO user, ConstraintValidatorContext constraintValidatorContext) {
        if(user.getName() == null )  return true;
        int myid = user.getId();

        List<User> allUser = userRepository.findAll();
        for (int i = 0; i < allUser.size(); i++) {
            System.out.println(allUser.get(i).getName().toLowerCase().equals(user.getName().toLowerCase()));
            if (allUser.get(i).getId() != myid) {
                if (allUser.get(i).getName().toLowerCase().equals(user.getName().toLowerCase())) return false;
            }
        }
        return true;
    }
}