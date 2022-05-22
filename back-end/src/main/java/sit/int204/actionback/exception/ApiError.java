package sit.int204.actionback.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.security.Timestamp;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ApiError {

    private String timestamp;
    private int status;
    private String message;
    private HttpStatus errors;
    private String path;


    public ApiError(String timestamp, int status,HttpStatus errors, String message , String path) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.errors = errors;
        this.path = path;
        this.message = message;
    }

}
