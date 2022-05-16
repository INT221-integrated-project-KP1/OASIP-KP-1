package sit.int204.actionback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.EventDTO;
import sit.int204.actionback.dtos.EventDetailsBaseDTO;
import sit.int204.actionback.dtos.EventPageDTO;
import sit.int204.actionback.dtos.EventUpdateDTO;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.service.EventService;

import java.util.Optional;


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

    @GetMapping("")
    public EventPageDTO getEvent(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "4") int pageSize){
        return eventService.getEvent(page,pageSize);
    }

    @GetMapping("/all")
    public EventPageDTO getAllEvent(){
        return eventService.getAllEvent();
    }

    @GetMapping("/{id}")
    public EventDetailsBaseDTO getEventById(@PathVariable Integer id){
        return eventService.getSimpleEventById(id);
    }

    @PostMapping("")
    public ResponseEntity createTest(@RequestBody EventDTO newEvent){
       return  eventService.create(newEvent);
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable Integer id) {
        eventService.deleteEventById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody EventUpdateDTO update, @PathVariable int id) {
        return eventService.editEvent(update,id);
    }
}
