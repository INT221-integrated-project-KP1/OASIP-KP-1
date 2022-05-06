package sit.int204.actionback.repo;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.actionback.dtos.SimpleEventDTO;
import sit.int204.actionback.entities.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Integer> {
}