package sit.int204.actionback.service;

import io.jsonwebtoken.Claims;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.actionback.config.JwtRequestFilter;
import sit.int204.actionback.config.JwtTokenUtil;
import sit.int204.actionback.controller.FileController;
import sit.int204.actionback.dtos.*;
import sit.int204.actionback.entities.Event;
import sit.int204.actionback.entities.EventCategory;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.enumfile.Role;
import sit.int204.actionback.repo.EventCategoryRepository;
import sit.int204.actionback.repo.EventRepository;
import sit.int204.actionback.repo.UserRepository;
import sit.int204.actionback.utils.ListMapper;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventCategoryRepository eventCategoryRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    private UserRepository userRepository;

//    public EventPageDTO getEvent(int page, int pageSize,HttpServletRequest request) {
//        String requestTokenHeader = request.getHeader("Authorization");
//        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
//            String header = requestTokenHeader.substring(7);
//            String email = jwtTokenUtil.getUsernameFromToken(header);
//            String myRole = userRepository.findByEmail(email).getRole();
//            System.out.println(myRole);
//            System.out.println(myRole.equals((Role.STUDENT).toString()));
//            System.out.println(email);
//            if(myRole.equals((Role.STUDENT).toString())){
//                List<Event> te = eventRepository.findAllByBookingEmail(email, PageRequest.of(page, pageSize));
//                System.out.println("test " + te.get(0).getBookingName());
//
//                return listMapper.mapList(te, EventPageDTO.class, modelMapper);
//            }
//        }
//        return listMapper.mapList(eventRepository.findAll(PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), EventPageDTO.class);
//    }

    public List<SimpleEventDTO> getAllEvent(HttpServletRequest request){
        String requestTokenHeader = request.getHeader("Authorization");
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")){
            String token = requestTokenHeader.substring(7);
            String email = jwtTokenUtil.getUsernameFromToken(token);
            Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
            String myRole = claims.get("role").toString().split("_")[0];

            if(myRole.equals(Role.STUDENT.toString())){
                return listMapper.mapList(eventRepository.findAllByBookingEmail(email ,Sort.by(Sort.Direction.DESC, "eventStartTime")), SimpleEventDTO.class, modelMapper);
            } else if (myRole.equals(Role.ADMIN.toString())){
                return listMapper.mapList(eventRepository.findAll(Sort.by(Sort.Direction.DESC, "eventStartTime")), SimpleEventDTO.class, modelMapper);
            } else if(myRole.equals(Role.LECTURER.toString())) {
//                int leuturerId = user.getId();
                System.out.println("asdsddss");
                System.out.println(eventRepository.findAllEventByLecturerEmail(email).size());
                return listMapper.mapList(eventRepository.findAllEventByLecturerEmail(email), SimpleEventDTO.class, modelMapper);
            }
        }
          return listMapper.mapList(eventRepository.findAll(Sort.by(Sort.Direction.DESC, "eventStartTime")), SimpleEventDTO.class, modelMapper);
    }

    public List<BlindedEventDto> getAllBlindedEvent(){
        return listMapper.mapList(eventRepository.findAll(Sort.by(Sort.Direction.DESC, "eventStartTime")), BlindedEventDto.class, modelMapper);
    }


    public List<SimpleEventDTO> getAllEventFilterByEventCategoryAndPassOrFutureOrAll(HttpServletRequest request, Integer eventCategoryId, String pastOrFutureOrAll, String date, int offsetMin, int page, int pageSize){
        String requestTokenHeader = request.getHeader("Authorization");
        String myRole = "";
        String email = "";
        int leuturerId = 0;
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String header = requestTokenHeader.substring(7);
            email = jwtTokenUtil.getUsernameFromToken(header);
            myRole = userRepository.findByEmail(email).getRole();
            User user = userRepository.findByEmail(email);
            leuturerId = user.getId();
        }
        if(leuturerId != 0){
            List<Event> event = eventRepository.findAllEventByLecturerCategory(leuturerId);
            return listMapper.mapList(event, SimpleEventDTO.class, modelMapper);
        }

        if(myRole.equals((Role.STUDENT).toString())){
            if(date.equals("")){
                if(eventCategoryId <= 0){
                    if(pastOrFutureOrAll.equals("future")){
                        return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventStartTimeAfter(email, Instant.now(), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
                    } else if (pastOrFutureOrAll.equals("past")){
                        return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventStartTimeBefore(email, Instant.now(), PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
                    }
                    return listMapper.mapList(eventRepository.findAllByBookingEmailAndIdNot(email, eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
                }

                if(pastOrFutureOrAll.equals("future")){
                    return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventStartTimeAfterAndEventCategoryId(email, Instant.now(), eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
                } else if (pastOrFutureOrAll.equals("past")){
                    return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventStartTimeBeforeAndEventCategoryId(email, Instant.now(), eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
                }
                return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventCategoryId(email, eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            } else {
                //UTC To GMT แปลง UTC จากทั้งคู่เป็น GMT แล้วเช็คด้วย GMT ทั้งคู่
                //offsetMin เช่น -420 = +07:00
                Instant input = Instant.parse(date).plus(offsetMin, ChronoUnit.MINUTES);
                System.out.println(input);
                long dayInMilli = 86400000;
                if(eventCategoryId > 0){
                    return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventCategoryIdAndEventStartTimeBetween(email, eventCategoryId, Instant.ofEpochMilli(input.toEpochMilli()), Instant.ofEpochMilli(input.toEpochMilli()+dayInMilli-1), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
                } else {
                    return listMapper.mapList(eventRepository.findAllByBookingEmailAndEventStartTimeBetween(email, Instant.ofEpochMilli(input.toEpochMilli()), Instant.ofEpochMilli(input.toEpochMilli()+dayInMilli-1), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
                }
            }
        }
        //==========================================================

        if(date.equals("")){
            if(eventCategoryId <= 0){
                if(pastOrFutureOrAll.equals("future")){
                    return listMapper.mapList(eventRepository.findAllByEventStartTimeAfter(Instant.now(), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
                } else if (pastOrFutureOrAll.equals("past")){
                    return listMapper.mapList(eventRepository.findAllByEventStartTimeBefore(Instant.now(), PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
                }
                return listMapper.mapList(eventRepository.findAllByIdNot(eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            }

            if(pastOrFutureOrAll.equals("future")){
                return listMapper.mapList(eventRepository.findAllByEventStartTimeAfterAndEventCategoryId(Instant.now(), eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
            } else if (pastOrFutureOrAll.equals("past")){
                return listMapper.mapList(eventRepository.findAllByEventStartTimeBeforeAndEventCategoryId(Instant.now(), eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
            }
            return listMapper.mapList(eventRepository.findAllByEventCategoryId(eventCategoryId, PageRequest.of(page, pageSize, Sort.by("eventStartTime").descending())), SimpleEventDTO.class, modelMapper);
        } else {
            //UTC To GMT แปลง UTC จากทั้งคู่เป็น GMT แล้วเช็คด้วย GMT ทั้งคู่
            //offsetMin เช่น -420 = +07:00
            Instant input = Instant.parse(date).plus(offsetMin, ChronoUnit.MINUTES);
            System.out.println(input);
            long dayInMilli = 86400000;
            if(eventCategoryId > 0){
                return listMapper.mapList(eventRepository.findAllByEventCategoryIdAndEventStartTimeBetween(eventCategoryId, Instant.ofEpochMilli(input.toEpochMilli()), Instant.ofEpochMilli(input.toEpochMilli()+dayInMilli-1), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
            } else {
                return listMapper.mapList(eventRepository.findAllByEventStartTimeBetween(Instant.ofEpochMilli(input.toEpochMilli()), Instant.ofEpochMilli(input.toEpochMilli()+dayInMilli-1), PageRequest.of(page, pageSize, Sort.by("eventStartTime").ascending())), SimpleEventDTO.class, modelMapper);
            }
        }





    }

    public ResponseEntity deleteEventById(Integer id, HttpServletRequest request) {

        String requestTokenHeader = request.getHeader("Authorization");
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String header = requestTokenHeader.substring(7);
            String email = jwtTokenUtil.getUsernameFromToken(header);
            Claims claims = jwtTokenUtil.getAllClaimsFromToken(header);
            String myRole = claims.get("role").toString().split("_")[0];
            Event event = eventRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, " id " + id +
                            "Does Not Exist !!!"
                    ));
            if(!myRole.equals((Role.ADMIN).toString())){
                if(!email.equals(event.getBookingEmail())){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your Booking Email is not match with your account");
                }
            }
            eventRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You dont login pls login");
    }



    public ResponseEntity getSimpleEventById(Integer id , HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("Authorization");
        System.out.println("getSimpleEventById");
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String header = requestTokenHeader.substring(7);
            String email = jwtTokenUtil.getUsernameFromToken(header);
            Claims claims = jwtTokenUtil.getAllClaimsFromToken(header);
            String myRole = claims.get("role").toString().split("_")[0];
            System.out.println(myRole);
            Event event = eventRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, " id " + id +
                            "Does Not Exist !!!"
                    ));
            System.out.println("getSimpleEventById2");
            if(myRole.equals((Role.STUDENT).toString())){
                if(email.equals(event.getBookingEmail())){
                    return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(event, EventDetailsBaseDTO.class));
                } else{
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your Booking Email is not match with your account");
                }
            }
            if(myRole.equals((Role.LECTURER).toString())){
                List<Event> eventAllOfThisLecturer = eventRepository.findAllEventByLecturerEmail(email);
                for (int i = 0; i < eventAllOfThisLecturer.size(); i++) {
                    if(event.getEventCategory().getId().equals(eventAllOfThisLecturer.get(i).getEventCategory().getId())){
                        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(event, EventDetailsBaseDTO.class));
                    }
                }
            }
            if(myRole.equals((Role.ADMIN).toString())){
                return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(event, EventDetailsBaseDTO.class));
            }

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You dont login pls login");
    }


    public ResponseEntity create(EventDTO newEvent, HttpServletRequest request) throws MessagingException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String header = requestTokenHeader.substring(7);
            String email = jwtTokenUtil.getUsernameFromToken(header);
            Claims claims = jwtTokenUtil.getAllClaimsFromToken(header);
            String myRole = claims.get("role").toString().split("_")[0];
            if(!myRole.equals((Role.ADMIN).toString())){
                if(!email.equals(newEvent.getBookingEmail())){
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your Booking Email is not match with your account");
                }
            }
        }
        Integer newEventDuration = eventCategoryRepository.findEventCategoryById(newEvent.getEventCategory().getId()).getEventDuration();
        Event e = modelMapper.map(newEvent, Event.class);
        e.setEventDuration(newEventDuration);

        eventRepository.saveAndFlush(e);
        Event event2Send = eventRepository.findById(e.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " +
                        "Does Not Exist !!!"
                ));
        sendmail(event2Send);

        System.out.println("Created");
        return ResponseEntity.status(HttpStatus.CREATED).body(e);

    }

    private void sendmail(Event event) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("tusking8030@gmail.com", "pqxowijwdepcbwna");
            }
        });
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm").withZone(ZoneId.of("UTC"));

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("oasipsy1@gmail.com", false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(event.getBookingEmail()));
        msg.setSubject("Your booking is complete.");
        msg.setContent("Your booking name : " + event.getBookingName() +
                        "<br> Event category : " + event.getEventCategory() +
                        "<br>Start date and time : " + formatter.format(event.getEventStartTime()) +
                        "<br>Event duration : " + event.getEventDuration() + " minitues"+
                        "<br>Event note : " + event.getEventNotes()
                , "text/html; charset=utf-8");
        msg.setSentDate(new Date());

        Transport.send(msg);
    }





    public ResponseEntity editEvent(EventUpdateDTO editEvent , int id ,HttpServletRequest request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " + id +
                        "Does Not Exist !!!"
                ));

        String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = requestTokenHeader.substring(7);
        String email = jwtTokenUtil.getUsernameFromToken(jwtToken);
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtToken);
        String myRole = claims.get("role").toString().split("_")[0];
        System.out.println("myrole" + myRole);
        System.out.println("myrole" + email);
        System.out.println(myRole.equals((Role.ADMIN).toString()));
        System.out.println(event.getBookingEmail().equals(email));
        if(!myRole.equals((Role.ADMIN).toString())){
            if(!event.getBookingEmail().equals(email)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Deleted Event booking email is not match with your email");
            }
        }
//    public ResponseEntity editEvent(EventUpdateDTO editEvent , int id ,BindingResult bindingResult)throws BindException {

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
        System.out.println(editEvent.getAttachment());
        event.setAttachment(editEvent.getAttachment());

        ///
//        fileStorageService.deleteFile(editEvent.getAttachment());
//        if(editEvent.getAttachment() != null){}

//        if(editEvent.getAttachment() == event.getAttachment()){
//            eventRepository.saveAndFlush(event);
//
//        }
//        else if(editEvent.getAttachment() == null){
//            fileStorageService.deleteFile(editEvent.getAttachment());
//            event.setAttachment(null);
//        }
//        else if(editEvent.getAttachment() != event.getAttachment()){
//            fileStorageService.deleteFile(editEvent.getAttachment());
//
//        }
        eventRepository.saveAndFlush(event);


        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    public List<EventCheckOverDTO> getAllEventForOverLabFront(Integer eventId,Integer categoryId, String startTime){
        if(eventId != 0){
            categoryId = eventRepository.findById(eventId).get().getEventCategory().getId();
            System.out.println(categoryId);
        }
        Instant input = Instant.parse(startTime);
        long maxDuration = 480 *60 *1000;

        return listMapper.mapList(eventRepository.findAllByIdNotAndEventCategoryIdAndEventStartTimeBetween(eventId, categoryId, Instant.ofEpochMilli(input.toEpochMilli()-maxDuration-1), Instant.ofEpochMilli(input.toEpochMilli()+maxDuration+1), PageRequest.of( 0, Integer.MAX_VALUE, Sort.by("eventStartTime").descending())), EventCheckOverDTO.class, modelMapper);
    }




    public boolean isOverLab(EventOverLabDTO event, int id){
        long newEventStartTimeMilli = event.getEventStartTime().toEpochMilli();
        long newDurationMilli =  eventCategoryRepository.findEventCategoryById(event.getEventCategory().getId()).getEventDuration() * 60 * 1000;

        List<Event> eventList = eventRepository.findAllByEventCategoryIdAndEventStartTimeBetween(event.getEventCategory().getId(), Instant.ofEpochMilli(newEventStartTimeMilli).minus(480, ChronoUnit.MINUTES), Instant.ofEpochMilli(newEventStartTimeMilli).plus(480, ChronoUnit.MINUTES), PageRequest.of(0, Integer.MAX_VALUE));

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
