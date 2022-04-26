package sit.int204.actionback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int204.actionback.dtos.EventDetailsBaseDTO;
import sit.int204.actionback.dtos.SimpleEventDTO;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.repo.EventCategoryRepository;
import sit.int204.actionback.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/api")
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
    private EventCategoryRepository EventCategoryRepository;

    @GetMapping("/EventCategory")
    public List<EventCategory> getEventCategory(){
        return EventCategoryRepository.findAll();
    }
}
