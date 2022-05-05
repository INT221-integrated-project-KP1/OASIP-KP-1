package sit.int204.actionback.controller;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.EventDetailsBaseDTO;
import sit.int204.actionback.dtos.SimpleEventDTO;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.repo.EventRepository;
import sit.int204.actionback.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/kp1/scheduled")
//@CrossOrigin(origins = "http://10.0.0.1:3000")
@CrossOrigin(origins = "*")
public class EventController {
    @GetMapping("/hello")
    public String home() {
        return "Hello Docker World";
    }

    @Autowired
    private EventService eventService;

    @GetMapping("")
    public List<SimpleEventDTO> getEvent(){
        return eventService.getEvent();
    }

    @GetMapping("/{id}")
    public EventDetailsBaseDTO getEventById(@PathVariable Integer id){
        return eventService.getSimpleEventById(id);
    }
    
    @PostMapping("")
    public void createTest(@RequestBody Event newEvent){
         eventService.create(newEvent);
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable Integer id) {
         eventService.deleteEventById(id);
    }


}
