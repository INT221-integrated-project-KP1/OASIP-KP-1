package sit.int204.actionback.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sit.int204.actionback.dtos.UserMatchingDTO;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.repo.UserRepository;
import sit.int204.actionback.utils.ListMapper;

import java.util.Optional;

@Service
public class MatchingService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    public ResponseEntity matchPassword(UserMatchingDTO userToMatch) {
        Optional <User> user = userRepository.findByEmail(userToMatch.getEmail());
        if(user.isEmpty()){
            return ResponseEntity.status(404).body("Invalid Email");
        }

        Argon2 argon2 = Argon2Factory.create();
        if (argon2.verify(user.get().getPassword(), userToMatch.getPassword())) {
            return ResponseEntity.status(200).body("Matched");
        }
        return ResponseEntity.status(401).body("Invalid Password");
    }


}
