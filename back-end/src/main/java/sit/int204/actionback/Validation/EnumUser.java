package sit.int204.actionback.Validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OverlabVaildation.class)
@Documented
public @interface EnumUser {
    String message() default "not have this role";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
