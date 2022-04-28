package sit.int204.actionback.controller;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.EventDetailsBaseDTO;
import sit.int204.actionback.dtos.SimpleEventDTO;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.repo.EventCategoryRepository;
import sit.int204.actionback.service.EventService;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api")
// @CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "*")
public class EventController {
    @GetMapping("/hello")
    public String home() {
        return "Hello Docker World";
    }

    @Autowired
    private EventService eventService;

    @GetMapping("/scheduled")
    public List<SimpleEventDTO> getEvent(){
        return eventService.getEvent();
    }

    @GetMapping("/scheduled/{id}")
    public EventDetailsBaseDTO getEventById(@PathVariable Integer id){
        return eventService.getSimpleEventById(id);
    }

    @Autowired
    private EventRepository eventrepository;
    
    @PostMapping("/scheduled/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Event createTest(@RequestBody Event newEvent){
     return eventrepository.saveAndFlush(newEvent);
    }

    @DeleteMapping("/scheduled/delete")
    public void deleteTest() {
        eventService.deleteAll();
    }

    @Autowired
    private EventCategoryRepository EventCategoryRepository;

    @GetMapping("/EventCategory")
    public List<EventCategory> getEventCategory(){
        return EventCategoryRepository.findAll();
    }
}
