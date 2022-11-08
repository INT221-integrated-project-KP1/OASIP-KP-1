package sit.int204.actionback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.service.EventCategoryOwnerService;
import sit.int204.actionback.service.EventCategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/eventcategoryowner")
@CrossOrigin(origins = "*")
public class EventCategoryOwnerController {
    @Autowired
    private EventCategoryOwnerService eventcategoryownerservice;


    @GetMapping("/{id}")
    public ResponseEntity getEventCategoryOwner(@PathVariable Integer id){
        return eventcategoryownerservice.getOwnerByUserId(id);
    }


}