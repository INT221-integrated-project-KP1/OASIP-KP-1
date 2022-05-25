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
import sit.int204.actionback.exception.ApiTestException;
import sit.int204.actionback.repo.EventCategoryRepository;
import sit.int204.actionback.repo.EventRepository;
import sit.int204.actionback.utils.ListMapper;
import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.util.List;

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


    public EventPageDTO getEvents(int page, int pageSize) {
        return modelMapper.map(repository.findAll(PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), EventPageDTO.class);
    }

    public List<SimpleEventDTO> getAllEvents(){
        return listMapper.mapList(repository.findAll(), SimpleEventDTO.class,modelMapper);
    }


    public List<SimpleEventDTO> getAllEventsFilterByEventCategoryAndPassOrFutureOrAll(Integer eventCategoryId, String pastOrFutureOrAll, String date, int offsetMin, int page, int pageSize){
        if(date.equals("")){
            if(eventCategoryId <= 0){
                if(pastOrFutureOrAll.equals("future")){
                    return listMapper.mapList(repository.findAllByEventStartTimeAfter(Instant.now(), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
                } else if (pastOrFutureOrAll.equals("past")){
                    return listMapper.mapList(repository.findAllByEventStartTimeBefore(Instant.now(), PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
                }
                return listMapper.mapList(repository.findAllByIdNot(eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            }

            if(pastOrFutureOrAll.equals("future")){
                return listMapper.mapList(repository.findAllByEventStartTimeAfterAndEventCategoryId(Instant.now(), eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
            } else if (pastOrFutureOrAll.equals("past")){
                return listMapper.mapList(repository.findAllByEventStartTimeBeforeAndEventCategoryId(Instant.now(), eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            }
            return listMapper.mapList(repository.findAllByEventCategoryId(eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
        } else {
            //UTC To GMT แปลง UTC จากทั้งคู่เป็น GMT แล้วเช็คด้วย GMT ทั้งคู่
            //offsetMin เช่น -420 = +07:00
            Instant input = Instant.parse(date).plus(offsetMin, ChronoUnit.MINUTES);
            System.out.println(input);
            long dayInMilli = 86400000;
            if(eventCategoryId > 0){
                return listMapper.mapList(repository.findAllByEventCategoryIdAndEventStartTimeBetween(eventCategoryId, Instant.ofEpochMilli(input.toEpochMilli()), Instant.ofEpochMilli(input.toEpochMilli()+dayInMilli-1), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
            } else {
                return listMapper.mapList(repository.findAllByEventStartTimeBetween(Instant.ofEpochMilli(input.toEpochMilli()), Instant.ofEpochMilli(input.toEpochMilli()+dayInMilli-1), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
            }
        }


    }

    public ResponseEntity deleteEventById(Integer id) {
        repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " + id +
                        "Does Not Exist !!!"
                ));
                repository.deleteById(id);
       return ResponseEntity.status(HttpStatus.OK).body(id);

    }



    public EventDetailsBaseDTO getSimpleEventById(Integer id) {
        Event event = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " + id +
                        "Does Not Exist !!!"
                ));
        return modelMapper.map(event, EventDetailsBaseDTO.class);
    }

    public ResponseEntity create(EventDTO newEvent) {

        Integer newEventDuration = eventCategoryRepository.findEventCategoryById(newEvent.getEventCategory().getId()).getEventDuration();

        Event e = modelMapper.map(newEvent, Event.class);

        e.setEventDuration(newEventDuration);


            repository.saveAndFlush(e);



            return ResponseEntity.status(HttpStatus.CREATED).body(e);
    }


    public ResponseEntity editEvent(EventUpdateDTO editEvent , int id ) {

//    public ResponseEntity editEvent(EventUpdateDTO editEvent , int id ,BindingResult bindingResult)throws BindException {
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
        if(!isOverLab(new EventOverLabDTO(editEvent.getEventStartTime(), eventCategory, eventDuration), id)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OverLaped");
        }

        event.setEventStartTime(editEvent.getEventStartTime());
        event.setEventNotes(editEvent.getEventNotes());

        repository.saveAndFlush(event);


        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    public List<EventCheckOverDTO> getAllEventsForOverLabFront(Integer eventId,Integer categoryId, String startTime){
        if(eventId != 0){
            categoryId = repository.findById(eventId).get().getEventCategory().getId();
            System.out.println(categoryId);
        }
        Instant input = Instant.parse(startTime);
        long maxDuration = 480 *60 *1000;

        return listMapper.mapList(repository.findAllByIdNotAndEventCategoryIdAndEventStartTimeBetween(eventId, categoryId, Instant.ofEpochMilli(input.toEpochMilli()-maxDuration-1), Instant.ofEpochMilli(input.toEpochMilli()+maxDuration+1), PageRequest.of( 0, Integer.MAX_VALUE, Sort.by("eventStartTime").descending())), EventCheckOverDTO.class, modelMapper);
    }




    public boolean isOverLab(EventOverLabDTO event, int id){
        long newEventStartTimeMilli = event.getEventStartTime().toEpochMilli();
        long newDurationMilli =  eventCategoryRepository.findEventCategoryById(event.getEventCategory().getId()).getEventDuration() * 60 * 1000;

        List<Event> eventList = repository.findAllByEventCategoryIdAndEventStartTimeBetween(event.getEventCategory().getId(), Instant.ofEpochMilli(newEventStartTimeMilli).minus(480, ChronoUnit.MINUTES), Instant.ofEpochMilli(newEventStartTimeMilli).plus(480, ChronoUnit.MINUTES), PageRequest.of(0, Integer.MAX_VALUE));

        for (int i = 0; i < eventList.size(); i++) {
            System.out.println(eventList.size());

            if(id != eventList.get(i).getId()){ //เวลา update จะได้ไม่ต้องเช็คตัวมันเอง
                System.out.println("start Va5");

                long milliSecond = eventList.get(i).getEventStartTime().toEpochMilli();
                long duration = eventList.get(i).getEventDuration() * 60 * 1000;
                System.out.println("CategoryChecked");

                System.out.println("newstart+newdu"+ newEventStartTimeMilli+newDurationMilli);
                System.out.println(newEventStartTimeMilli+newDurationMilli);

                System.out.println("mill" +milliSecond);
                if(newEventStartTimeMilli+newDurationMilli > milliSecond && newEventStartTimeMilli+newDurationMilli < milliSecond+duration){
                    System.out.println("Overlab1+4");
                    return false;
                }
                else if (newEventStartTimeMilli > milliSecond && newEventStartTimeMilli < milliSecond+duration){
                    System.out.println("Overlab2+4");
                    return false;
                }
                else if (newEventStartTimeMilli <= milliSecond && newEventStartTimeMilli+newDurationMilli >= milliSecond){
                    System.out.println("Overlab3");
                    return false;
                }
            }
        }
        return true;



    }


    public boolean checkTimeFuture(long eventStartTime){
        if(eventStartTime+60*1000 > Instant.now().toEpochMilli()) {

            return true;
        }
        return false;
    }








}
