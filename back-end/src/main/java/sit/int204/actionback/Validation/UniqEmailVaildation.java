package sit.int204.actionback.Validation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import sit.int204.actionback.dtos.UserAddDTO;

import sit.int204.actionback.entities.User;
import sit.int204.actionback.repo.UserRepository;
import sit.int204.actionback.service.UserService;
import sit.int204.actionback.utils.ListMapper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Optional;

public class UniqEmailVaildation implements ConstraintValidator<UniqEmail, UserAddDTO> {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void initialize(UniqEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserAddDTO user, ConstraintValidatorContext constraintValidatorContext) {
        if(user.getEmail() == null )  return true;

        int myid = 0 ;
        if(user.getId() == null){myid = -1;}
        else {myid = user.getId();}

        List<UserAddDTO> allUser  = listMapper.mapList(userRepository.findAll(), UserAddDTO.class, modelMapper);
        for (int i = 0 ; i < allUser.size() ; i++){
            if(allUser.get(i).getId() != myid){
                if(allUser.get(i).getEmail().equals(user.getEmail())) return false;
            }

        }
        return true;
    }
}
