package sit.int204.actionback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.*;
import sit.int204.actionback.repo.EventRepository;
import sit.int204.actionback.service.EventService;

import java.io.IOException;
import java.util.List;
import sit.int204.actionback.exception.ApiTestException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("api/event")
@CrossOrigin(origins = "*")
public class EventController {


    @Autowired
    private EventService eventService;

//    @GetMapping("")
//    public EventPageDTO getEvent(@RequestParam(defaultValue = "0") int page,
//                                 @RequestParam(defaultValue = "4") int pageSize ,HttpServletRequest request) {
//        return eventService.getEvent(page, pageSize ,request);
//    }

    @GetMapping("/all")
    public List<SimpleEventDTO> getAllEvent(HttpServletRequest request) {
        System.out.println("Test 2 ");
        return eventService.getAllEvent(request);
    }
    @GetMapping("/blinded")
    public List<BlindedEventDto> getAllBlindedEvent() {
        System.out.println("Test 2 ");
        return eventService.getAllBlindedEvent();
    }

    @GetMapping("/overlapping")
    public List<EventCheckOverDTO> getAllEventForOverLabCheck(@RequestParam(defaultValue = "0") Integer eventId,
                                                                @RequestParam(defaultValue = "0") Integer eventCategoryId,
                                                              @RequestParam String startTime) {
        return eventService.getAllEventForOverLabFront(eventId, eventCategoryId, startTime);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEventById(@PathVariable Integer id , HttpServletRequest request) {
        return eventService.getSimpleEventById(id , request);
    }

    @GetMapping("/filtration")
    public List<SimpleEventDTO> getEventByFilterCategory(@RequestParam(defaultValue = "0") int eventCategoryId,
                                                         @RequestParam(defaultValue = "all") String pastOrFutureOrAll,
                                                         @RequestParam(defaultValue = "") String date,
                                                         @RequestParam(defaultValue = "0") int offsetMin,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "4") int pageSize,
                                                         HttpServletRequest request) {
        return eventService.getAllEventFilterByEventCategoryAndPassOrFutureOrAll(request, eventCategoryId, pastOrFutureOrAll, date, offsetMin, page, pageSize);
    }

    @PostMapping("/adding")
    public ResponseEntity createTest(@Valid @RequestBody EventDTO newEvent, HttpServletRequest request) throws MethodArgumentNotValidException, MessagingException, IOException {
        System.out.println("postmapping");
        return eventService.create(newEvent, request);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deleteTest(@PathVariable Integer id, HttpServletRequest request) {
        return eventService.deleteEventById(id, request);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody EventUpdateDTO update, @PathVariable int id, HttpServletRequest request) {
        return eventService.editEvent(update, id, request);
    }

}

