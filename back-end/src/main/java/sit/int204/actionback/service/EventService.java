package sit.int204.actionback.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import sit.int204.actionback.dtos.*;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.exception.ApiTestException;
import sit.int204.actionback.repo.EventCategoryRepository;
import sit.int204.actionback.repo.EventRepository;
import sit.int204.actionback.utils.ListMapper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.naming.Binding;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Autowired
    private ApiTestException apiTestException;

    public EventPageDTO getEvent(int page, int pageSize) {
        return modelMapper.map(repository.findAll(PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), EventPageDTO.class);
    }

    public List<SimpleEventDTO> getAllEvent(){
        return listMapper.mapList(repository.findAll(), SimpleEventDTO.class,modelMapper);
    }


    public List<SimpleEventDTO> getAllEventFilterByEventCategoryAndPassOrFutureOrAll(Integer eventCategoryId, String pastOrFutureOrAll, String date, int offsetMin, int page, int pageSize){
        if(date.equals("")){
            if(eventCategoryId == 0){
                if(pastOrFutureOrAll.equals("future")){
                    return listMapper.mapList(repository.findAllByEventStartTimeAfter(Instant.now(), PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
                } else if (pastOrFutureOrAll.equals("past")){
                    return listMapper.mapList(repository.findAllByEventStartTimeBefore(Instant.now(), PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
                }
                return listMapper.mapList(repository.findAllByIdNot(eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            }

            if(pastOrFutureOrAll.equals("future")){
                return listMapper.mapList(repository.findAllByEventStartTimeAfterAndEventCategoryId(Instant.now(), eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            } else if (pastOrFutureOrAll.equals("past")){
                return listMapper.mapList(repository.findAllByEventStartTimeBeforeAndEventCategoryId(Instant.now(), eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            }
            return listMapper.mapList(repository.findAllByEventCategoryId(eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
        } else {
            //UTC To GMT แปลง UTC จากทั้งคู่เป็น GMT แล้วเช็คด้วย GMT ทั้งคู่
            //offsetMin เช่น -420 = +07:00
            Instant input = Instant.parse(date).plus(offsetMin, ChronoUnit.MINUTES);

            long dayInMilli = 86400000;
            if(eventCategoryId != 0){
                return listMapper.mapList(repository.findAllByEventCategoryIdAndEventStartTimeBetween(eventCategoryId, Instant.ofEpochMilli(input.toEpochMilli()), Instant.ofEpochMilli(input.toEpochMilli()+dayInMilli-1), PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            } else {
                return listMapper.mapList(repository.findAllByEventStartTimeBetween(Instant.ofEpochMilli(input.toEpochMilli()), Instant.ofEpochMilli(input.toEpochMilli()+dayInMilli-1), PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            }
        }


    }

    public EventDetailsBaseDTO getSimpleEventById(Integer id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " + id +
                        "Does Not Exist !!!"
                ));
        return modelMapper.map(event, EventDetailsBaseDTO.class);
    }

    public ResponseEntity create(EventDTO newEvent)throws MethodArgumentNotValidException {
//       if(!checkTimeFuture(newEvent.getEventStartTime().toEpochMilli())){
//           System.out.println("ss");
//           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Time Future Pls");
//       }

        int setEventDuration = (eventCategoryRepository.findById(newEvent.getEventCategory().getId())).get().getEventDuration();
        System.out.println(setEventDuration);

        newEvent.setEventDuration(setEventDuration);
        if (isOverLab(new EventOverLabDTO(newEvent.getEventStartTime(), newEvent.getEventCategory(), newEvent.getEventDuration()), 0)) {
//            bindingResult.addError(new FieldError("EventDTO", "eventStartTime", "overlab"));
//            bindingResult.addError(new ObjectError("kub", "overlab"));
            return new ResponseEntity<>("overlab" , HttpStatus.BAD_REQUEST);

        }

        Event e = modelMapper.map(newEvent, Event.class);
//        if (!(bindingResult.hasErrors())) {
            repository.saveAndFlush(e);
            System.out.println("Created");
            return ResponseEntity.status(HttpStatus.CREATED).body(e);
            //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("CANT CREATE");
//        }
//        else throw new MethodArgumentNotValidException(null,bindingResult);

    }
    public boolean isOverLab(EventOverLabDTO event, int id){
        System.out.println("start");
        long minuteInMillisecond = 0;
        long newMillisecond = event.getEventStartTime().toEpochMilli();
        long newDuration = event.getEventDuration() * 60 * 1000;
        int categoryId = event.getEventCategory().getId();
        System.out.println(categoryId);;
        List<Event> eventList = repository.findAllByEventCategoryId(categoryId, PageRequest.of(0, Integer.MAX_VALUE));
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
        if(eventStartTime+60*1000 > Instant.now().toEpochMilli()) {

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Time Future Pls");
        }

        int eventDuration = event.getEventDuration();
        EventCategory eventCategory = event.getEventCategory();
        if(isOverLab(new EventOverLabDTO(editEvent.getEventStartTime(), eventCategory, eventDuration), id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OverLab");
        }

        event.setEventStartTime(editEvent.getEventStartTime());
        event.setEventNotes(editEvent.getEventNotes());
        repository.saveAndFlush(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

//    public boolean checkEmail(String email){
//        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
//        Matcher m = p.matcher(email);
//        boolean matchFound = m.matches();
//        if(matchFound) {
//            System.out.println("that is email");
//            return true;
//        }
//        else {
//            return false;
//        }
//    }

//    public boolean checkEventDuration(int duration){
//        if(duration >=1 && duration <= 480){
//            System.out.println("invalid Duration");
//            return true;
//        }
//        return false;
//    }


//VALIDATE-INPUT-LENGTH
//    public boolean checkCountName(String Name){
//        if(Name.length() > 100 ){
//            System.out.println("length of name more than 100");
//            return false;
//        }
//        return true;
//    }
//    public boolean checkFields(Event event){
//        if( event.getId() != null){
//            System.out.println("No ID for this event");
//            return false;
//        }
//        if( event.getBookingName() != null){
//            System.out.println("No BookingName for this event");
//            return false;
//        }
//        if( event.getBookingEmail() != null){
//            System.out.println("No BookingEmail for this event");
//            return false;
//        }
//        if( event.getEventStartTime() != null){
//            System.out.println("No Time for this event");
//            return false;
//        }
//        if( event.getEventCategory() != null){
//            System.out.println("No EventCategoryID for this event");
//            return false;
//        }
//        if( event.getEventDuration() != null){
//            System.out.println("No Duration for this event");
//            return false;
//        }
//        return true;
//    }


}
