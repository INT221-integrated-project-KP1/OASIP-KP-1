package sit.int204.actionback.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.actionback.dtos.EventCategoryOwnerDTO;
import sit.int204.actionback.dtos.UserShowDTO;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.entities.EventCategoryOwner;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.repo.EventCategoryOwnerRepository;
import sit.int204.actionback.repo.EventCategoryRepository;
import sit.int204.actionback.repo.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class EventCategoryOwnerService {
    @Autowired
    public EventCategoryOwnerRepository eventCategoryOwnerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EventCategoryRepository eventCategoryRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity updateEventCategoryOwner(EventCategoryOwnerDTO e){
        //id, user_id, eventcategory_id

        List<Integer> current_owner = eventCategoryOwnerRepository.getOwnersId(e.getEventCategoryId());
        List<Integer> new_owner = new ArrayList<Integer>();

        // [1,2,3,4,5,6]
        // [2,3]
        boolean isAdd = true;
        if(e.getUserId().length == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("AT LEAST 1");
        }



        for (int i = 0; i < e.getUserId().length; i++) {
            isAdd = true;
            for (int j = 0; j < current_owner.size(); j++) {
                if(current_owner.get(j) == e.getUserId()[i]){
                    isAdd = false;
                }
            }
            if(isAdd){
                new_owner.add(e.getUserId()[i]);
                //addEventCategoryOwner
                eventCategoryOwnerRepository.addEventCategoryOwner(e.getEventCategoryId(), e.getUserId()[i]);
            }
        }
        boolean match = false;
        List<Integer> current2_owner = eventCategoryOwnerRepository.getOwnersId(e.getEventCategoryId());
        for (int i = 0; i < current2_owner.size(); i++) {
            match = false;
            for (int j = 0; j < e.getUserId().length; j++) {
                if(current2_owner.get(i) == e.getUserId()[j]){
                    match = true;
                }
            }
            if(!match){
                eventCategoryOwnerRepository.deleteOwner(current2_owner.get(i));
            }
        }
        return ResponseEntity.status(201).body("ok");
    }

    public ResponseEntity getEventCategoryOwnerByEventCategoryId(Integer idEventCategory){
        EventCategory ec = eventCategoryRepository.findAllById(idEventCategory);
        List<EventCategoryOwner> eco = eventCategoryOwnerRepository.findEventCategoryOwnerByEventCategory(ec);
        List<UserShowDTO> userShowDTOList = new ArrayList<>();
        for (int i = 0; i < eco.size(); i++) {
            UserShowDTO userShowDTO = new UserShowDTO();
            userShowDTO.setId(eco.get(i).getUser().getId());
            userShowDTO.setEmail(eco.get(i).getUser().getEmail());
            userShowDTO.setName(eco.get(i).getUser().getName());
            userShowDTOList.add(userShowDTO);
        }


        return ResponseEntity.status(HttpStatus.OK).body(userShowDTOList);
    }

//    public ResponseEntity getAll(){
//        List<EventCategoryOwner> eco = eventCategoryOwnerRepository.getAll();
//        List<UserShowDTO> userShowDTOList = new ArrayList<>();
//        for (int i = 0; i < eco.size(); i++) {
//            UserShowDTO userShowDTO = new UserShowDTO();
//            userShowDTO.setId(eco.get(i).getUser().getId());
//            userShowDTO.setEmail(eco.get(i).getUser().getEmail());
//            userShowDTO.setName(eco.get(i).getUser().getName());
//            userShowDTOList.add(userShowDTO);
//        }
//
//
//        return ResponseEntity.status(HttpStatus.OK).body(userShowDTOList);
//    }


    public String deleteForOwner(Integer user_id){
        User u = userRepository.findById(user_id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, " id " + user_id +
                "Does Not Exist !!!"
        ));

//        lecName +
//        " is the owner of" + Project Management Clinic, DevOps/Infra Clinic, Server-side Clinic.
//        " You cannot delete this user account since" +
//        lecName +
//        "is the only owner of " +
//        Project Management Clinic, DevOps/Infra Clinic. +
//        " Another owner must be added to the event category(s) before this lecturer can be deleted."
        String lecName = u.getName();
        String nameThatLengthOne =  " is the only owner of ";
        String eventcategoryAll = " is the owner of ";
        List<EventCategory> ec = eventCategoryRepository.findAllEventCategoryByLecturerEmail(u.getEmail());
        for (int i = 0; i < ec.size(); i++) {
            List<EventCategoryOwner> eco = eventCategoryOwnerRepository.findEventCategoryOwnerByEventCategory(ec.get(i));
            if(eco.size() <= 1){
                //cant delete
                nameThatLengthOne = nameThatLengthOne + " " + eco.get(0).getEventCategory().getEventCategoryName() + ", ";
            } else{
                eventcategoryAll = eventcategoryAll + " " + ec.get(i).getEventCategoryName();
            }
        }
        if(!nameThatLengthOne.equals(" is the only owner of ")){
            return lecName + eventcategoryAll + " You cannot delete this user account since " + lecName + nameThatLengthOne + " Another owner must be added to the event category(s) before this lecturer can be deleted.";
            //false old
        }

        System.out.println("deleteForOwner");
        List<EventCategoryOwner> owner = eventCategoryOwnerRepository.getOwnerByUserId(user_id);
        if (!owner.isEmpty()) {
            System.out.println("iasfjiofhjasifhsiohfijoh");
            eventCategoryOwnerRepository.deleteOwner(user_id);
        }
        System.out.println("deleteForOwner2");

        return "success";
    }
    public ResponseEntity allEventCategoryByLecturerEmail(Integer user_id){
        User u = userRepository.findById(user_id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, " id " + user_id +
                "Does Not Exist !!!"
        ));
        String eventcategoryAll = "";
        String eventCategorySolo = "";
        List<EventCategory> ec = eventCategoryRepository.findAllEventCategoryByLecturerEmail(u.getEmail());
        for (int i = 0; i < ec.size(); i++) {
            List<EventCategoryOwner> eco = eventCategoryOwnerRepository.findEventCategoryOwnerByEventCategory(ec.get(i));
            if(eco.size() <= 1){
                eventCategorySolo = eventCategorySolo + ", " + ec.get(i).getEventCategoryName();
            } else{
                eventcategoryAll = eventcategoryAll + " " + ec.get(i).getEventCategoryName();
            }
        }
        HashMap<String, String> objectToResponse = new HashMap<String, String>();
        objectToResponse.put("eventCategoryAll", eventcategoryAll);
        objectToResponse.put("eventCategorySolo", eventCategorySolo);
        return ResponseEntity.status(HttpStatus.OK).body(objectToResponse);
    }


}

