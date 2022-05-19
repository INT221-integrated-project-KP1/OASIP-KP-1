package sit.int204.actionback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sit.int204.actionback.dtos.EventDTO;
import sit.int204.actionback.dtos.EventDetailsBaseDTO;
import sit.int204.actionback.dtos.EventPageDTO;
import sit.int204.actionback.dtos.EventUpdateDTO;
import sit.int204.actionback.dtos.SimpleEventDTO;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.repo.EventCategoryRepository;
import sit.int204.actionback.repo.EventRepository;
import sit.int204.actionback.service.EventService;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import sit.int204.actionback.exception.ApiTestException;
import sit.int204.actionback.service.EventService;

import javax.validation.Valid;
import java.util.*;


@RestController
@RequestMapping("api/scheduled")
@CrossOrigin(origins = "*")
public class EventController {
    @GetMapping("/hello")
    public String home() {
        return "Hello Docker World";
    }

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository eventRepository;

    private ApiTestException apiTestException;
    @GetMapping("")
    public EventPageDTO getEvent(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "4") int pageSize){
        return eventService.getEvent(page,pageSize);
    }

    @GetMapping("/all")
    public List<SimpleEventDTO> getAllEvent(){
        return eventService.getAllEvent();
    }

    @GetMapping("/{id}")
    public EventDetailsBaseDTO getEventById(@PathVariable Integer id){
        return eventService.getSimpleEventById(id);
    }

    @GetMapping("/filter")
    public List<SimpleEventDTO> getEventByFilterCategory(@RequestParam(defaultValue = "0") int eventCategoryId,
                                                         @RequestParam(defaultValue = "all") String pastOrFutureOrAll,
                                                         @RequestParam(defaultValue = "") String date,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "4") int pageSize){
        return eventService.getAllEventFilterByEventCategoryAndPassOrFutureOrAll(eventCategoryId, pastOrFutureOrAll, date, page, pageSize);
    }

    @PostMapping("")
    public ResponseEntity createTest(@Valid @RequestBody EventDTO newEvent ) throws MethodArgumentNotValidException {

    return eventService.create(newEvent);
   }



//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.hasErrors());


    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable Integer id) {
        eventService.deleteEventById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody EventUpdateDTO update, @PathVariable int id) {
        return eventService.editEvent(update,id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}

//    public ResponseEntity<Object> handleMethodArgumentNotValid
//            (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
//        {
//
//        Map<String, String> errors = new HashMap<>();
//        System.out.println(ex);
//        ex.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
////        Object o = new Object(errors)
////        ErrorOne errorone = new ErrorOne(errors);
////        return new ResponseEntity<>(errorone,HttpStatus.BAD_REQUEST);
//            return errors;
//    }

//     class ErrorOne {
//        public ErrorOne(Map<String, String> errors) {
//            this.errors = errors;
//        }
//
//        public Map<String, String> getError() {
//            return errors;
//        }
//
//        public void setError(Map<String, String> errors) {
//            this.errors = errors;
//        }
//    }


