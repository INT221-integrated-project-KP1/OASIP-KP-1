package sit.int204.actionback.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import sit.int204.actionback.dtos.EventDTO;
import sit.int204.actionback.dtos.UserDTO;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.enumfile.Role;
import sit.int204.actionback.repo.EventCategoryRepository;
import sit.int204.actionback.repo.EventRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Optional;

public class EnumUserVaildation implements ConstraintValidator<EnumUser,String > {

    @Autowired
    private EventRepository repository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

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