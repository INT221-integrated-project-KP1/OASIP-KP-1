package sit.int204.actionback.Validation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import sit.int204.actionback.dtos.UserDTO;
import sit.int204.actionback.repo.UserRepository;
import sit.int204.actionback.service.UserService;
import sit.int204.actionback.utils.ListMapper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqNameVaildation implements ConstraintValidator<UniqName, UserDTO> {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void initialize(UniqName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserDTO user, ConstraintValidatorContext constraintValidatorContext) {
        int myid = 0 ;
        if(user.getId() == null) myid = -1 ;
        else myid = user.getId();
        if (user.getName() == null ) return true;
        List<UserDTO> allUser = listMapper.mapList(userRepository.findAll(), UserDTO.class, modelMapper);
        for (int i = 0; i < allUser.size(); i++) {
            System.out.println(allUser.get(i).getName().toLowerCase().equals(user.getName().toLowerCase()));
            if (allUser.get(i).getId() != myid) {
                if (allUser.get(i).getName().toLowerCase().equals(user.getName().toLowerCase())) return false;
            }
        }
        return true;
    }
}