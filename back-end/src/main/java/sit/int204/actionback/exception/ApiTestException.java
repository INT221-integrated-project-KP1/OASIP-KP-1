package sit.int204.actionback.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiTestException extends ResponseEntityExceptionHandler {
//    @Override
//    public ResponseEntity<Object> handleMethodArgumentNotValid
//            (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
//        Map<String, String> errors = new HashMap<>();
//        System.out.println(ex);
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
////        Object o = new Object(errors)
//        ErrorOne errorone = new ErrorOne(errors);
//        return new ResponseEntity<>(errorone,HttpStatus.BAD_REQUEST);
//    }
//
//    class ErrorOne {
//        private Map<String, String> details;
//
//        public ErrorOne(Map<String, String> details) {
//            this.details = details;
//        }
//
//        public Map<String, String> getDetails() {
//            return details;
//        }
//
//        public void setDetails(Map<String, String> details) {
//            this.details = details;
//        }
//    }
}
