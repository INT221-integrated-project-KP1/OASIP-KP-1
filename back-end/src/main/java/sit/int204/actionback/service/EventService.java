package sit.int204.actionback.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import sit.int204.actionback.dtos.EventDetailsBaseDTO;
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

    public List<SimpleEventDTO> getEvent() {
        List<Event> event = repository.findAllByOrderByEventStartTimeDesc();
        return listMapper.mapList(event, SimpleEventDTO.class, modelMapper);
    }

    public EventDetailsBaseDTO getSimpleEventById(Integer id) {
        Event event = repository.findById(id)
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id "+ id+
                        "Does Not Exist !!!"
                ));
        return modelMapper.map(event, EventDetailsBaseDTO.class);
}

    public void deleteAll() {
        repository.deleteAll();
    }
}
