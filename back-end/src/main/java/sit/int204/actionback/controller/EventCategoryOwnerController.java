package sit.int204.actionback.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.EventCategoryOwnerDTO;

import sit.int204.actionback.entities.EventCategoryOwner;
import sit.int204.actionback.repo.EventCategoryOwnerRepository;
import sit.int204.actionback.service.EventCategoryOwnerService;

import java.util.List;

@RestController
@RequestMapping("api/eventcategoryowner")
@CrossOrigin(origins = "*")
public class EventCategoryOwnerController {

    @Autowired
    EventCategoryOwnerService eventCategoryOwnerService;

    @Autowired
    EventCategoryOwnerRepository eventCategoryOwnerRepository;

    @PostMapping("")
    public ResponseEntity addEventCategoryOwner(@RequestBody EventCategoryOwnerDTO info) {
        return eventCategoryOwnerService.updateEventCategoryOwner(info);
    }

//    @GetMapping("")
//    public ResponseEntity getAllEventCategoryOwner() {
//        return eventCategoryOwnerService.getAll();
//    }

    @GetMapping("{id}")
    public ResponseEntity getEventCategoryOwnerByEventCategoryId(@PathVariable Integer id) {
        return eventCategoryOwnerService.getEventCategoryOwnerByEventCategoryId(id);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity update(@Valid @RequestBody EventCategory updateEventCategory, @PathVariable Integer id, HttpServletRequest request) {
//        return eventCategoryService.updateEventCategory(updateEventCategory,id, request);
//    }

    @GetMapping("getOwner/{id}")
    public ResponseEntity getEventCategoryOwner(@PathVariable Integer id) {
        return eventCategoryOwnerService.allEventCategoryByLecturerEmail(id);
    }
}
