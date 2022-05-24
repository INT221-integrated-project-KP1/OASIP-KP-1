package sit.int204.actionback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.repo.EventCategoryRepository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class EventCategoryService {
    @Autowired
    public EventCategoryRepository eventCategoryRepository;

    public ResponseEntity findCategories(){

        List<EventCategory> eventCategory = eventCategoryRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(eventCategory);
    }


    public ResponseEntity updateEventCategory(EventCategory updateEventCategory , Integer id) {
        EventCategory eventCategory = eventCategoryRepository.findEventCategoryById(id);

        if(!checkEventDuration(updateEventCategory.getEventDuration())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Invalid Duration");
        }

        if(!checkEventCategoryName(updateEventCategory.getEventCategoryName(), id)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Invalid EventCategoryName");
        }
        System.out.println("3");

        eventCategory.setEventCategoryName(updateEventCategory.getEventCategoryName());
        eventCategory.setEventDuration(updateEventCategory.getEventDuration());
        eventCategory.setEventCategoryDescription(updateEventCategory.getEventCategoryDescription());
        eventCategoryRepository.saveAndFlush(eventCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventCategory);
    }



    //Check Update Category
    public boolean checkEventDuration(Integer duration){
        if(duration >=1 && duration <= 480){
            return true;
        }
        return false;
    }

    public boolean checkEventCategoryName(String eventCategoryName, Integer id){
        if(eventCategoryRepository.findAllById(id).getEventCategoryName().toLowerCase().equals(eventCategoryName.toLowerCase())){
            //ซ้ำกับชื่อเดิม = ไม่เปลี่ยนชื่อ
            //หรืออาจจะเปลียนพิิมพ์เล็กใหญ่
            return true;
        }
        System.out.println("2");
        //TusKungZ
        //eventCategoryName = TUSKUNGZ
        if(eventCategoryRepository.findByEventCategoryNameIgnoreCase(eventCategoryName) == null){
            //ไม่ซ้ำ
            return true;
        }

        System.out.println("Duplicate EventCategoryName");
        return false;
    }


}
