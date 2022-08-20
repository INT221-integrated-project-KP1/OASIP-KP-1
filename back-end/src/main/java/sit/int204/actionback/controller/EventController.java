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
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {


    @Autowired
    private EventService eventService;

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
    public List<EventCheckOverDTO> getAllEventForOverLabCheck(@RequestParam(defaultValue = "0") Integer eventId,
                                                                @RequestParam(defaultValue = "0") Integer eventCategoryId,
                                                              @RequestParam String startTime) {
        return eventService.getAllEventForOverLabFront(eventId, eventCategoryId, startTime);
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


    @DeleteMapping("/{id}")
    public ResponseEntity deleteTest(@PathVariable Integer id) {
        return eventService.deleteEventById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody EventUpdateDTO update, @PathVariable int id) {
        return eventService.editEvent(update, id);
    }

}

