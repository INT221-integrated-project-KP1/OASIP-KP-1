package sit.int204.actionback.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.service.EventCategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/eventcategory")
@CrossOrigin(origins = "*")
public class EventCategoryController {
    @Autowired
    private EventCategoryService eventCategoryService;


    @GetMapping("")
    public ResponseEntity getEventCategory(){
            return eventCategoryService.findCategory();
    }



    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody EventCategory updateEventCategory, @PathVariable Integer id, HttpServletRequest request) {
        return eventCategoryService.updateEventCategory(updateEventCategory,id, request);
    }

}
