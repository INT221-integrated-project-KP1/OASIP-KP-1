package sit.int204.actionback.controller;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.EventCategoryOwnerDTO;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.entities.EventCategoryOwner;
import sit.int204.actionback.repo.EventCategoryOwnerRepository;
import sit.int204.actionback.service.EventCategoryOwnerService;
import sit.int204.actionback.service.EventCategoryService;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
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
//    public List<EventCategoryOwner> getAllEventCategoryOwner() {
//        return eventCategoryOwnerRepository.getAll();
//    }
//
//    @GetMapping("{id}")
//    public List<EventCategoryOwner> getEventCategoryOwnerByEventCategoryId(@PathVariable Integer id) {
//        return eventCategoryOwnerService.getEventCategoryOwnerByEventCategoryId(id);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity update(@Valid @RequestBody EventCategory updateEventCategory, @PathVariable Integer id, HttpServletRequest request) {
//        return eventCategoryService.updateEventCategory(updateEventCategory,id, request);
//    }

}
