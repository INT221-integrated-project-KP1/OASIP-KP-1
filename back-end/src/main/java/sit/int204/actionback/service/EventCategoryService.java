package sit.int204.actionback.service;

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
    public EventCategoryRepository eventCategoryRepository;

    public ResponseEntity updateEventCategory(EventCategory updateEventCategory , int id) {
        if(!checkEventDuration(updateEventCategory.getEventDuration())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Invalid Duration");
        }

        if(!checkEventCategoryName(updateEventCategory.getEventCategoryName(), id)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Invalid EventCategoryName");
        }

        eventCategoryRepository.saveAndFlush(updateEventCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body("Updated !!");
    }

    public boolean checkEventDuration(int duration){
        if(duration >=1 && duration <= 480){
            System.out.println("valid Duration");
            return true;
        }
        return false;
    }

    public boolean checkEventCategoryName(String eventCategoryName, int id){
        if(eventCategoryRepository.findEventCategoryNameById(id).toLowerCase() == eventCategoryName.toLowerCase()){
            //ซ้ำกับชื่อเดิม = ไม่เปลี่ยนชื่อ
            //หรืออาจจะเปลียนพิิมพ์เล็กใหญ่
            return true;
        }

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
