package sit.int204.actionback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.actionback.repo.EventCategoryOwnerRepository;
import sit.int204.actionback.repo.EventCategoryRepository;
@Service
public class EventCategoryOwnerService {
    @Autowired
    public EventCategoryOwnerRepository eventCategoryOwnerRepository;

    public boolean deleteForOwner(Integer user_id){
        eventCategoryOwnerRepository.deleteOwner(user_id);
        return true;
    }
}

