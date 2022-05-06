package sit.int204.actionback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.repo.EventCategoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/EventCategory/KP1")
//@CrossOrigin(origins = "http://10.0.0.1:3000")
@CrossOrigin(origins = "*")
public class EventCategoryController {

    @GetMapping("/hello2")
    public String home() {
        return "Hello Docker World 2 ";
    }

    @Autowired
    private EventCategoryRepository EventCategoryRepository;

    @GetMapping("")
    public List<EventCategory> getEventCategory(){
        return EventCategoryRepository.findAll();
    }
}
