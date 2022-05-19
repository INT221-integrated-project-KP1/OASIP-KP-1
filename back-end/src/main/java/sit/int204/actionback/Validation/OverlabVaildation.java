package sit.int204.actionback.Validation;

import org.springframework.beans.factory.annotation.Autowired;
import sit.int204.actionback.dtos.EventDTO;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.repo.EventCategoryRepository;
import sit.int204.actionback.repo.EventRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Instant;
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
        System.out.println("start Va");
        Optional<EventCategory> eventCategoryDuration = eventCategoryRepository.findById(eventDTO.getEventCategory().getId());

        long minuteInMillisecond = 0;
        long newMillisecond = eventDTO.getEventStartTime().toEpochMilli();
        long newDuration =  eventCategoryDuration.get().getEventDuration() * 60 * 1000;
        System.out.println(eventCategoryDuration.get().getEventDuration()+"eventCategoryDuration.get().getEventDuration()");
        System.out.println(newDuration+"newDuration");
        int categoryId = eventDTO.getEventCategory().getId();
        System.out.println(categoryId);;
        List<Event> eventList = repository.findAllByEventCategoryId(categoryId);

        for (int i = 0; i < eventList.size(); i++) {
            if(!(0 == eventList.get(i).getId())){ //เวลา update จะได้ไม่ต้องเช็คตัวมันเอง
                long milliSecond = eventList.get(i).getEventStartTime().toEpochMilli();
                long duration = eventList.get(i).getEventDuration() * 60 * 1000;
                System.out.println("CategoryChecked");
                if(newMillisecond+newDuration+minuteInMillisecond > milliSecond && newMillisecond+newDuration-minuteInMillisecond < milliSecond+duration){
                    System.out.println("Overlab1+4");
                    // return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                    return false;
                }
                else if (newMillisecond+minuteInMillisecond > milliSecond && newMillisecond-minuteInMillisecond < milliSecond+duration){
                    System.out.println("Overlab2+4");
                    return false;
                    //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                }
                else if (newMillisecond-minuteInMillisecond < milliSecond && newMillisecond+newDuration+minuteInMillisecond > milliSecond+duration){
                    System.out.println("Overlab3");
                    return false;
                    //return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("OverLab");
                }
                System.out.println(newMillisecond-minuteInMillisecond);
                System.out.println(milliSecond);
                System.out.println("***");
                System.out.println(newMillisecond+duration+minuteInMillisecond);
                System.out.println(milliSecond+duration);
            }
        }
        return true;



//   return false;
    }
}
