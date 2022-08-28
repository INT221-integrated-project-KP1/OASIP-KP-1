package sit.int204.actionback.Validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqNameVaildation.class)
@Documented
public @interface UniqName {
    String message() default "is not Uniqueness name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
