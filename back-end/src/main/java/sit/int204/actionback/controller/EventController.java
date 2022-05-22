package sit.int204.actionback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.*;
import sit.int204.actionback.repo.EventRepository;
import sit.int204.actionback.service.EventService;

import java.util.List;

import sit.int204.actionback.exception.ApiTestException;

import javax.validation.Valid;


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
                                 @RequestParam(defaultValue = "4") int pageSize) {
        return eventService.getEvent(page, pageSize);
    }

    @GetMapping("/all")
    public List<SimpleEventDTO> getAllEvent() {
        return eventService.getAllEvent();
    }

    @GetMapping("/overlabcheck")
    public List<EventCheckOverDTO> getAllEventForOverLabCheck(@RequestParam Integer eventCategoryId,
                                                              @RequestParam String startTime) {
        return eventService.getAllEventForOverLabFront(eventCategoryId, startTime);
    }

    @GetMapping("/{id}")
    public EventDetailsBaseDTO getEventById(@PathVariable Integer id) {
        return eventService.getSimpleEventById(id);
    }

    @GetMapping("/filter")
    public List<SimpleEventDTO> getEventByFilterCategory(@RequestParam(defaultValue = "0") int eventCategoryId,
                                                         @RequestParam(defaultValue = "all") String pastOrFutureOrAll,
                                                         @RequestParam(defaultValue = "") String date,
                                                         @RequestParam(defaultValue = "0") int offsetMin,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "4") int pageSize) {
        return eventService.getAllEventFilterByEventCategoryAndPassOrFutureOrAll(eventCategoryId, pastOrFutureOrAll, date, offsetMin, page, pageSize);
    }

    @PostMapping("")
    public ResponseEntity createTest(@Valid @RequestBody EventDTO newEvent) throws MethodArgumentNotValidException {
        System.out.println("postmapping");
        return eventService.create(newEvent);
    }


//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.hasErrors());


    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable Integer id) {
        eventService.deleteEventById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody EventUpdateDTO update, @PathVariable int id) {
        return eventService.editEvent(update, id);
    }

}

