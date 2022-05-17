package sit.int204.actionback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.EventUpdateDTO;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.repo.EventCategoryRepository;
import sit.int204.actionback.service.EventCategoryService;

import java.util.List;

@RestController
@RequestMapping("api/eventcategory")
@CrossOrigin(origins = "*")
public class EventCategoryController {
    private EventCategoryService eventCategoryService;

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

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody EventCategory updateEventCategory, @PathVariable int id) {
        return eventCategoryService.updateEventCategory(updateEventCategory,id);
    }
}
