package sit.int204.actionback.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
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

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Paths;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class ApiTestException extends ResponseEntityExceptionHandler{
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
            ) {
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
        ZonedDateTime timeStamp = ZonedDateTime.now();
        ApiError apiError =
                new ApiError(timeStamp,400,HttpStatus.BAD_REQUEST,e,request.getDescription(false).split("=")[1]);
        return  new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);

    }
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<Object> handleUsernameNotFoundExceptionExceptions(String msg) {
//        System.out.println("All exceptions Method getting executed!!!!");
//
//        return new ResponseEntity(msg, HttpStatus.NOT_FOUND);
//    }


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAllExceptions(final Exception ex, final WebRequest request) {
//        System.out.println("All exceptions Method getting executed!!!!");
//
//        final List<String> details = new ArrayList<>();
//        details.add(ex.getMessage());
//        return new ResponseEntity(details, HttpStatus.NOT_FOUND);
//    }


}