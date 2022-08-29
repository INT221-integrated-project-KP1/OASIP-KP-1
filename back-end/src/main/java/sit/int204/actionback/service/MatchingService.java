package sit.int204.actionback.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sit.int204.actionback.dtos.UserMatchingDTO;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.repo.MatchingRepository;
import sit.int204.actionback.repo.UserRepository;
import sit.int204.actionback.utils.ListMapper;
@Service
public class MatchingService {
    @Autowired
    private MatchingRepository matchingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    public ResponseEntity matchPassword(UserMatchingDTO userToMatch) {
        User user = matchingRepository.findByEmail(userToMatch.getEmail());
        if(user == null){
            return ResponseEntity.status(404).body("Invalid Email");
        }

        Argon2 argon2 = Argon2Factory.create();
        if (argon2.verify(user.getPassword(), userToMatch.getPassword())) {
            return ResponseEntity.status(200).body("Matched");
        }
        return ResponseEntity.status(401).body("Invalid Password");
    }
}
