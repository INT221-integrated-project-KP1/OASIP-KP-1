package sit.int204.actionback.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.actionback.dtos.EventDetailsBaseDTO;
import sit.int204.actionback.dtos.EventPageDTO;
import sit.int204.actionback.dtos.ProductPageDTO;
import sit.int204.actionback.dtos.SimpleEventDTO;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.repo.EventRepository;
import sit.int204.actionback.utils.ListMapper;

import java.time.Instant;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    public EventPageDTO getEvent(int page , int pageSize) {
        return modelMapper.map(repository.findAll(
                PageRequest.of(page, pageSize , Sort.by("eventStartTime").descending())),
                EventPageDTO.class);

    }

    public EventDetailsBaseDTO getSimpleEventById(Integer id) {
        Event event = repository.findById(id)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id "+ id+
                        "Does Not Exist !!!"
                ));
        return modelMapper.map(event, EventDetailsBaseDTO.class);
}
        public ResponseEntity create(Event newEvent){
            System.out.println("start");
            long newMillisecond = newEvent.getEventStartTime().toEpochMilli();
            long newDuration = newEvent.getEventDuration() * 60 * 1000;
            int categoryId = newEvent.getEventCategory().getId();
            System.out.println(categoryId);;
            List<Event> eventList = repository.findAll();
            for (int i = 0; i < eventList.size(); i++) {
                System.out.println(eventList.get(i).getEventCategory().getId());
                if(categoryId == eventList.get(i).getEventCategory().getId()){
                    long milliSecond = eventList.get(i).getEventStartTime().toEpochMilli();
                    long duration = eventList.get(i).getEventDuration() * 60 * 1000;
                    System.out.println("CategoryChecked");
                    if(newMillisecond < milliSecond+duration && newMillisecond >= milliSecond){
                        System.out.println("Overlab");
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                    }
                    if(newMillisecond+newDuration <= milliSecond+duration && newMillisecond+newDuration > milliSecond){
                        System.out.println("Overlab");
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                    }
                    if(newMillisecond <= milliSecond && newMillisecond+newDuration >= milliSecond+newDuration){
                        System.out.println("Overlab");
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                    }
                }

            }
            repository.saveAndFlush(newEvent);
            System.out.println("Created");
            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
        }

    public void deleteEventById(Integer id) {
        repository.findById(id)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id "+ id+
                        "Does Not Exist !!!"
                ));
        repository.deleteById(id);
    }
}
