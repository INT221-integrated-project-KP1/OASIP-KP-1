package sit.int204.actionback.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ApiTestException extends ResponseEntityExceptionHandler{
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<String>();
        String e ="";
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
            e = e + error.getField() + " " +  error.getDefaultMessage()+";";
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
            e = e + error.getObjectName()+ " " + error.getDefaultMessage()+";";

        }
        String timeStamp = new Date().toString();

        ApiError apiError =
                new ApiError(timeStamp,400,HttpStatus.BAD_REQUEST,e,ex.getNestedPath());
        return handleExceptionInternal(
                ex, apiError, headers, apiError.getErrors(), request);
    }

//    @Override
// protected ResponseEntity<Object> handleMethodArgumentNotValid(
//        MethodArgumentNotValidException ex,
//         HttpHeaders headers,
//          HttpStatus status,
//          WebRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = "r";
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//    }
}