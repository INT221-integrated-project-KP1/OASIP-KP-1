package sit.int204.actionback.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.actionback.dtos.*;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.repo.EventCategoryRepository;
import sit.int204.actionback.repo.EventRepository;
import sit.int204.actionback.utils.ListMapper;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    @Autowired
    private EventCategoryRepository rep;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;
    private long milliSecond;

    public EventPageDTO getEvent(int page, int pageSize) {
        return modelMapper.map(repository.findAll(PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), EventPageDTO.class);

    }

    public EventDetailsBaseDTO getSimpleEventById(Integer id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " + id +
                        "Does Not Exist !!!"
                ));
        return modelMapper.map(event, EventDetailsBaseDTO.class);
    }

    public ResponseEntity create(EventDTO newEvent){
       if(!(checkEmail(newEvent.getBookingEmail()))){
           return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("value Email error");
       }
       if(!checkTimeFuture(newEvent.getEventStartTime().toEpochMilli())){
           System.out.println("ss");
           return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Time Future Pls");
       }

        int setEventDuration = (rep.findById(newEvent.getEventCategory().getId())).get().getEventDuration();
        System.out.println(setEventDuration);

        newEvent.setEventDuration(setEventDuration);
        if(!isOverLab(new EventOverLabDTO(newEvent.getEventStartTime(), newEvent.getEventCategory(), newEvent.getEventDuration()), 0)){
            Event e = modelMapper.map(newEvent, Event.class);
            repository.saveAndFlush(e);
            System.out.println("Created");
            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("CANT CREATE");
    }

    public boolean isOverLab(EventOverLabDTO event, int id){
        System.out.println("start");
        long minuteInMillisecond = 60 * 1000;
        long newMillisecond = event.getEventStartTime().toEpochMilli();
        long newDuration = event.getEventDuration() * 60 * 1000;
        int categoryId = event.getEventCategory().getId();
        System.out.println(categoryId);;
        List<Event> eventList = repository.findAll();
        for (int i = 0; i < eventList.size(); i++) {
            System.out.println(eventList.get(i).getEventCategory().getId());
            if(categoryId == eventList.get(i).getEventCategory().getId()){
                if(!(id == eventList.get(i).getId())){ //เวลา update จะได้ไม่ต้องเช็คตัวมันเอง
                    long milliSecond = eventList.get(i).getEventStartTime().toEpochMilli();
                    long duration = eventList.get(i).getEventDuration() * 60 * 1000;
                    System.out.println("CategoryChecked");
                    if(newMillisecond-minuteInMillisecond <= milliSecond && newMillisecond+newDuration+minuteInMillisecond >= milliSecond){
                        System.out.println("Overlab1");
                        // return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                        return true;
                    }
                    if(newMillisecond-minuteInMillisecond <= milliSecond+duration && newMillisecond+minuteInMillisecond >= milliSecond){
                        System.out.println("Overlab2");
                        return true;
                        //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                    }
                }
            }
        }
        return false;
    }


    public boolean checkTimeFuture(long eventStartTime){
        Date date = new Date();
        long timeMilli = date.getTime();
        if(eventStartTime+60*1000 >= timeMilli) {

            return true;
        }
        return false;
    }



    public void deleteEventById(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " + id +
                        "Does Not Exist !!!"
                ));
        repository.deleteById(id);
    }


    public ResponseEntity editEvent(EventUpdateDTO editEvent , int id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " + id +
                        "Does Not Exist !!!"
                ));
        if(!checkTimeFuture(editEvent.getEventStartTime().toEpochMilli())){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Time Future Pls");
        }

        int eventDuration = event.getEventDuration();
        EventCategory eventCategory = event.getEventCategory();



        if(!isOverLab(new EventOverLabDTO(editEvent.getEventStartTime(), eventCategory, eventDuration), id)){
            event.setEventStartTime(editEvent.getEventStartTime());
            event.setEventNotes(editEvent.getEventNotes());
            repository.saveAndFlush(event);
            return ResponseEntity.status(HttpStatus.CREATED).body(event);
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("CANT UPDATE EVENT");
    }

    public boolean checkEmail(String email){
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        boolean matchFound = m.matches();
        if(matchFound) {
            System.out.println("that is email");
            return true;
        }
        else {
            return false;
        }
    }



}

