package sit.int204.actionback.service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import sit.int204.actionback.config.JwtTokenUtil;
import sit.int204.actionback.dtos.SimpleEventDTO;
import sit.int204.actionback.dtos.UserAddDTO;
import sit.int204.actionback.dtos.UserModifyDTO;
import sit.int204.actionback.dtos.UserShowDTO;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.enumfile.Role;
import sit.int204.actionback.repo.UserRepository;
import sit.int204.actionback.utils.ListMapper;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventCategoryOwnerService eventCategoryOwnerService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private ListMapper listMapper;

    public List<User> getEvent(){
        return userRepository.findAll();
    }

    public List<UserShowDTO> getUserLecturer(){
        List<User> u = userRepository.findAll();
        List<User> u2 = new ArrayList<>();
        for (int i = 0; i < u.size(); i++) {
            if(u.get(i).getRole().equals(Role.LECTURER.toString())){
                u2.add(u.get(i));
            }
        }
        return listMapper.mapList(u2, UserShowDTO.class, modelMapper);
    }

    public ResponseEntity deleteUser(Integer id , HttpServletRequest request) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " + id +
                        "Does Not Exist !!!"
                ));
        System.out.println("testtt");
        String requestTokenHeader = request.getHeader("Authorization");
        String header = requestTokenHeader.substring(7);
        String email = jwtTokenUtil.getUsernameFromToken(header);
//        String myRole = userRepository.findByEmail(email).getRole();
        System.out.println(u.getRole());
        if(u.getRole().equals(Role.LECTURER.toString())){
            System.out.println("testtt");
            String forReturn = eventCategoryOwnerService.deleteForOwner(id);
            if(!forReturn.contains("EventCategory ที่อยู่คนเดียว: ")){
                userRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(id);
            } else{
                //ลบไม่ได้
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(forReturn);
            }
        }
        System.out.println("test33tt");

        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);

    }

    public ResponseEntity editUser(UserModifyDTO editUser , int id ) {

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
         return argon2.hash(22, 65536, 1, stringToHash);  //97 length of string
    }


    public ResponseEntity getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("THIS ID NOT EXIST: " + id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(user.get(), User.class));     }
}

