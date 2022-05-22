package sit.int204.actionback.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.security.Timestamp;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ApiError {

    private ZonedDateTime timestamp;
    private int status;
    private String message;
    private HttpStatus errors;
    private String path;


    public ApiError(ZonedDateTime timestamp, int status,HttpStatus errors, String message , String path) {
        super();
        this.timestamp = timestamp;
        this.status = status;
        this.errors = errors;
        this.path = path;
        this.message = message;
    }

}
