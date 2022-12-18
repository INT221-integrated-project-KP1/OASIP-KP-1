package sit.int204.actionback.service;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sit.int204.actionback.config.JwtTokenUtil;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.enumfile.Role;
import sit.int204.actionback.repo.EventCategoryRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class EventCategoryService {
    @Autowired
    public EventCategoryRepository eventCategoryRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    public ResponseEntity findCategory(){

        List<EventCategory> eventCategory = eventCategoryRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(eventCategory);
    }


    public ResponseEntity updateEventCategory(EventCategory updateEventCategory , Integer id, HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String email = jwtTokenUtil.getUsernameFromToken(jwtToken);
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
        String myRole = claims.get("role").toString().split("_")[0];
        if(myRole.equals(Role.STUDENT.toString())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Student Can not Do");
        }

        EventCategory eventCategory = eventCategoryRepository.findEventCategoryById(id);

        boolean isCan = false;
        if(myRole.equals(Role.LECTURER.toString())){
            List<EventCategory> ListEventCategoryOfThisEmail = eventCategoryRepository.findAllEventCategoryByLecturerEmail(email);
            for (int i = 0; i < ListEventCategoryOfThisEmail.size(); i++) {
                if(eventCategory.getId().equals(ListEventCategoryOfThisEmail.get(i).getId())){
                    isCan = true;
                }
            }
            if(!isCan){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Only on ur own");
            }
        }


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
