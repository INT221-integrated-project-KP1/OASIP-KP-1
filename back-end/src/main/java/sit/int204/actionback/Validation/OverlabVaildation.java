package sit.int204.actionback.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import sit.int204.actionback.dtos.EventDTO;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.repo.EventCategoryRepository;
import sit.int204.actionback.repo.EventRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.List;
import java.util.Optional;

public class OverlabVaildation implements ConstraintValidator<Overlab, EventDTO > {

    @Autowired
    private EventRepository repository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Override
    public void initialize(Overlab constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(EventDTO eventDTO, ConstraintValidatorContext constraintValidatorContext) {
        long newEventStartTimeMilli = eventDTO.getEventStartTime().toEpochMilli();
        long newDurationMilli =  eventCategoryRepository.findEventCategoryById(eventDTO.getEventCategory().getId()).getEventDuration() * 60 * 1000;

        List<Event> eventList = repository.findAllByEventCategoryIdAndEventStartTimeBetween(eventDTO.getEventCategory().getId(),
                Instant.ofEpochMilli(newEventStartTimeMilli).minus(480, ChronoUnit.MINUTES),
                Instant.ofEpochMilli(newEventStartTimeMilli).plus(480, ChronoUnit.MINUTES),
                PageRequest.of(0, Integer.MAX_VALUE));

        for (int i = 0; i < eventList.size(); i++) {
            System.out.println(eventList.size());

            if(0 != eventList.get(i).getId()){ //เวลา update จะได้ไม่ต้องเช็คตัวมันเอง
                System.out.println("start Va5");

                long milliSecond = eventList.get(i).getEventStartTime().toEpochMilli();
                long duration = eventList.get(i).getEventDuration() * 60 * 1000;
                System.out.println("CategoryChecked");

                System.out.println("newstart+newdu"+ newEventStartTimeMilli+newDurationMilli);
                System.out.println(newEventStartTimeMilli+newDurationMilli);

                System.out.println("mill" +milliSecond);
                if(newEventStartTimeMilli+newDurationMilli > milliSecond &&
                        newEventStartTimeMilli+newDurationMilli < milliSecond+duration){
                    System.out.println("Overlab1+4");
                    // return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                    return false;
                }
                else if (newEventStartTimeMilli > milliSecond &&
                        newEventStartTimeMilli < milliSecond+duration){
                    System.out.println("Overlab2+4");
                    return false;
                    //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                }
                else if (newEventStartTimeMilli < milliSecond &&
                        newEventStartTimeMilli+newDurationMilli > milliSecond){
                    System.out.println("Overlab3");
                    return false;
                    //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                } else if(newEventStartTimeMilli == milliSecond && newDurationMilli == duration){
                    System.out.println("OverLab5");
                    return false;
                }

            }
        }
        return true;



//   return false;
    }
}
