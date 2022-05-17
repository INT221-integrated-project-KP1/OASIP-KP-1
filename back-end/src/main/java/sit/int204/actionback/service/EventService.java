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
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;
    private long milliSecond;

    public EventPageDTO getEvent(int page, int pageSize) {
        return modelMapper.map(repository.findAll(PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), EventPageDTO.class);
    }

    public EventPageDTO getAllEvent(){
        return modelMapper.map(repository.findAll(), EventPageDTO.class);
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

        int setEventDuration = (eventCategoryRepository.findById(newEvent.getEventCategory().getId())).get().getEventDuration();
        System.out.println(setEventDuration);

        newEvent.setEventDuration(setEventDuration);
        if(isOverLab(new EventOverLabDTO(newEvent.getEventStartTime(), newEvent.getEventCategory(), newEvent.getEventDuration()), 0)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab Time");
        }
        Event e = modelMapper.map(newEvent, Event.class);
        repository.saveAndFlush(e);
        System.out.println("Created");
        return ResponseEntity.status(HttpStatus.CREATED).body("OK");


        //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("CANT CREATE");
    }

    public boolean isOverLab(EventOverLabDTO event, int id){
        System.out.println("start");
        long minuteInMillisecond = 60 * 1000;
        long newMillisecond = event.getEventStartTime().toEpochMilli();
        long newDuration = event.getEventDuration() * 60 * 1000;
        int categoryId = event.getEventCategory().getId();
        System.out.println(categoryId);;
        List<Event> eventList = repository.findAllByEventCategoryId(categoryId);
        for (int i = 0; i < eventList.size(); i++) {
                if(!(id == eventList.get(i).getId())){ //เวลา update จะได้ไม่ต้องเช็คตัวมันเอง
                    long milliSecond = eventList.get(i).getEventStartTime().toEpochMilli();
                    long duration = eventList.get(i).getEventDuration() * 60 * 1000;
                    System.out.println("CategoryChecked");
                    if(newMillisecond+newDuration+minuteInMillisecond > milliSecond && newMillisecond+newDuration-minuteInMillisecond < milliSecond+duration){
                        System.out.println("Overlab1+4");
                        // return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                        return true;
                    }
                    else if (newMillisecond+minuteInMillisecond > milliSecond && newMillisecond-minuteInMillisecond < milliSecond+duration){
                        System.out.println("Overlab2+4");
                        return true;
                        //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                    }
                    else if (newMillisecond-minuteInMillisecond < milliSecond && newMillisecond+newDuration+minuteInMillisecond > milliSecond+duration){
                        System.out.println("Overlab3");
                        return true;
                        //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                    }
                    System.out.println(newMillisecond-minuteInMillisecond);
                    System.out.println(milliSecond);
                    System.out.println("***");
                    System.out.println(newMillisecond+duration+minuteInMillisecond);
                    System.out.println(milliSecond+duration);
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
        if(isOverLab(new EventOverLabDTO(editEvent.getEventStartTime(), eventCategory, eventDuration), id)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
        }

        event.setEventStartTime(editEvent.getEventStartTime());
        event.setEventNotes(editEvent.getEventNotes());
        repository.saveAndFlush(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
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

    public boolean checkEventDuration(int duration){
        if(duration >=1 && duration <= 480){
            System.out.println("invalid Duration");
            return true;
        }
        return false;
    }

    public boolean checkEventCategoryName(String eventCategoryName){
        if(eventCategoryRepository.findByEventCategoryName(eventCategoryName) == null){
            System.out.println("Duplicate EventCategoryName");
            return false;
        }
        return true;
    }

    //VALIDATE-INPUT-LENGTH
    public boolean checkCountName(String Name){
        if(Name.length() > 100 ){
            System.out.println("length of name more than 100");
            return false;
        }
        return true;
    }

    public boolean checkFields(Event event){
        if( event.getId() != null){
            System.out.println("No ID for this event");
            return false;
        }
        if( event.getBookingName() != null){
            System.out.println("No BookingName for this event");
            return false;
        }
        if( event.getBookingEmail() != null){
            System.out.println("No BookingEmail for this event");
            return false;
        }
        if( event.getEventStartTime() != null){
            System.out.println("No Time for this event");
            return false;
        }
        if( event.getEventCategory() != null){
            System.out.println("No EventCategoryID for this event");
            return false;
        }
        if( event.getEventDuration() != null){
            System.out.println("No Duration for this event");
            return false;
        }
        return true;
    }



}
