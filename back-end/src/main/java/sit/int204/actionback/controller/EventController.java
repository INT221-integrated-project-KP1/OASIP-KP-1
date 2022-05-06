package sit.int204.actionback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.EventDetailsBaseDTO;
import sit.int204.actionback.dtos.EventPageDTO;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.service.EventService;


@RestController
@RequestMapping("/api/scheduled/KP1")
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
    public EventPageDTO getEvent(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int pageSize){
        return eventService.getEvent(page,pageSize);
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
