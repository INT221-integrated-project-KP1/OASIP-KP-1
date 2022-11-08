package sit.int204.actionback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sit.int204.actionback.entities.EventCategoryOwner;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.repo.EventCategoryOwnerRepository;

import java.util.Optional;

@Service
public class EventCategoryOwnerService {
    @Autowired
    public EventCategoryOwnerRepository eventCategoryOwnerRepository;

    public boolean deleteForOwner(Integer user_id){
        Optional<EventCategoryOwner> owner = eventCategoryOwnerRepository.findByUserId(user_id);
        if (!owner.isEmpty()) {
            eventCategoryOwnerRepository.deleteOwner(user_id);
        }
        return true;
    }

    public ResponseEntity getOwnerByUserId(Integer user_id){
        Optional<EventCategoryOwner> owner = eventCategoryOwnerRepository.findByUserId(user_id);
        return ResponseEntity.status(HttpStatus.OK).body(owner);
    }

    public ResponseEntity addOwnerByUserId(){
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    public ResponseEntity deleteOwnerByUserId(){
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}

