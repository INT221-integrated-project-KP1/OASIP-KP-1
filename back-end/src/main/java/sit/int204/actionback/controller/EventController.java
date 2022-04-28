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

    @PostMapping("/scheduled/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTest(){
        eventService.createEvent(1, "Somchai Jaidee (OR-7)", "somchai.jai@mail.kmutt.ac.th", "", Instant.parse("2018-11-30T18:35:24.00Z"), 2, 30);
        eventService.createEvent(2, "Somsri Rakdee (SJ-3)", "somsri.rak@mail.kmutt.ac.th", "ขอปรึกษาปัญหาเพื่อนไม่ช่วยงาน", Instant.parse("2565-12-02T23:00:10Z"), 1, 30);
        eventService.createEvent(3, "สมเกียรติ ขยันเรียน กลุ่ม TT-4", "somkiat.kay@kmutt.ac.th", "", Instant.parse("2565-12-20T06:30:41Z"), 3, 15);
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
