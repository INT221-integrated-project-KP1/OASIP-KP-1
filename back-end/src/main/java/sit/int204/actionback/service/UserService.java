package sit.int204.actionback.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
<<<<<<< HEAD
import sit.int204.actionback.dtos.EventPageDTO;
import sit.int204.actionback.dtos.UserDTO;
import sit.int204.actionback.dtos.UserMatchingDTO;
import sit.int204.actionback.dtos.UserModifyDTO;
=======
import sit.int204.actionback.dtos.UserAddDTO;
>>>>>>> backend
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

    public List<User> getEvent(){
        return userRepository.findAll();
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

<<<<<<< HEAD
    public ResponseEntity editUser(UserModifyDTO editUser , int id ) {
=======
    public ResponseEntity editUser(UserAddDTO editUser , int id ) {
>>>>>>> backend

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " + id +
                        "Does Not Exist !!!"
                ));

        user.setName(editUser.getName());
        user.setEmail(editUser.getEmail());
        user.setRole(editUser.getRole());
        userRepository.editUser(user);


        return ResponseEntity.status(HttpStatus.CREATED).body(editUser);
    }

    public ResponseEntity addUser(UserAddDTO userToAdd) {
        System.out.println("UserService: ");

        User user = modelMapper.map(userToAdd, User.class);
        System.out.println("moelMapper success to User.class");
        // iterations = 10
        // memory = 64m
        // parallelism = 1
        System.out.println("test2222");
        System.out.println("test2223");
        System.out.println("test2224");
        System.out.println("test2225");
        System.out.println("test2226");
        user.setPassword(argon2Hashing(user.getPassword()));
      /*if (argon2.verify(hash, password)) {
                System.out.println("Hash matches password.");
      }*/
        System.out.println("Hashing Password SUceess");
        userRepository.saveUser(user);
        System.out.println("saveUser Success");
        return ResponseEntity.status(HttpStatus.CREATED).body("CREATED");
    }

    public String argon2Hashing(String stringToHash){
        System.out.println("argon2 hasing start");
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i, 8, 16);
        System.out.println("create Object argon2");
        return argon2.hash(22, 65536, 1, stringToHash); //97 length of string
    }


    public ResponseEntity getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("THIS ID NOT EXIST: " + id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(user.get(), User.class));     }
}

