package sit.int204.actionback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.actionback.dtos.*;
import sit.int204.actionback.repo.EventRepository;
import sit.int204.actionback.service.EventService;

import java.util.List;

import sit.int204.actionback.exception.ApiTestException;

import javax.validation.Valid;


@RestController
@RequestMapping("api/event")
@CrossOrigin(origins = "http://localhost:3000")

public class EventController {


    @Autowired
    private EventService eventService;

    @GetMapping("")
    public ResponseEntity getEvent(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "4") int pageSize) {
        return eventService.getEvents(page, pageSize);
    }

//    @GetMapping("/all")
//    public List<SimpleEventDTO> getAllEvents() {
//        return eventService.getAllEvents();
//    }

    @GetMapping("/overlabcheck")
    public ResponseEntity getAllEventForOverLabCheck(@RequestParam(defaultValue = "0") Integer eventId,
                                                                @RequestParam(defaultValue = "0") Integer eventCategoryId,
                                                              @RequestParam String startTime) {
        return eventService.getAllEventsForOverLabFront(eventId, eventCategoryId, startTime);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEventById(@PathVariable Integer id) {
        return eventService.getSimpleEventById(id);
    }

    @GetMapping("/filter")
    public ResponseEntity getEventByFilterCategory(@RequestParam(defaultValue = "0") int eventCategoryId,
                                                         @RequestParam(defaultValue = "all") String pastOrFutureOrAll,
                                                         @RequestParam(defaultValue = "") String date,
                                                         @RequestParam(defaultValue = "0") int offsetMin,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "4") int pageSize) {
        return eventService.getAllEventsFilterByEventCategoryAndPassOrFutureOrAll(
                eventCategoryId, pastOrFutureOrAll, date, offsetMin, page, pageSize);
    }

    @PostMapping("")
    public ResponseEntity create(@Valid @RequestBody EventDTO newEvent) {
        System.out.println("dasdsaddadsdadadadad");

        return eventService.create(newEvent);
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable Integer id) {
        eventService.deleteEventById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody EventUpdateDTO update, @PathVariable int id) {
        return eventService.editEvent(update, id);
    }

}

