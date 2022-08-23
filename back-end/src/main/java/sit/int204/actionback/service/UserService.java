package sit.int204.actionback.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import sit.int204.actionback.dtos.UserDTO;
import sit.int204.actionback.dtos.UserGetDTO;
import sit.int204.actionback.dtos.UserMatchingDTO;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.repo.UserRepository;
import sit.int204.actionback.utils.ListMapper;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    public List<UserGetDTO> getUserAll() {
        return listMapper.mapList(userRepository.findAllByOrderByNameAsc(), UserGetDTO.class, modelMapper);
    }

    public ResponseEntity addUser(UserDTO userToAdd) {
        User user = modelMapper.map(userToAdd, User.class);
        Argon2 argon2 = Argon2Factory.create();
        // iterations = 10
        // memory = 64m
        // parallelism = 1
        System.out.println(argon2Hashing(userToAdd.getPassword()));
        user.setPassword(argon2Hashing(userToAdd.getPassword()));
      /*if (argon2.verify(hash, password)) {
                System.out.println("Hash matches password.");
      }*/
        userRepository.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    public String argon2Hashing(String stringToHash){
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i, 8, 16);
        return argon2.hash(22, 65536, 1, stringToHash); //97 length of string
    }
    public ResponseEntity matchPassword(UserMatchingDTO userToMatch) {
        User user = userRepository.findByEmail(userToMatch.getEmail());
        if(user == null){
            return ResponseEntity.status(HttpStatus.CREATED).body("Invalid Email");
        }

        System.out.println(userToMatch);
        Argon2 argon2 = Argon2Factory.create();
        if (argon2.verify(user.getPassword(), userToMatch.getPassword())) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Matched");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Invalid Pass");
    }

    public ResponseEntity deleteUser(Integer id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " + id +
                        "Does Not Exist !!!"
                ));
        ;
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);

    }

    public ResponseEntity editUser(UserGetDTO editUser, int id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " + id +
                        "Does Not Exist !!!"
                ));

        user.setName(editUser.getName());
        user.setEmail(editUser.getEmail());
        user.setRole(editUser.getRole().toString());
        userRepository.editUser(user);


        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    public ResponseEntity getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("THIS ID NOT EXIST: " + id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(user.get(), User.class));
    }
}

