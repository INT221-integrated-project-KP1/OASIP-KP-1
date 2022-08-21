package sit.int204.actionback.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import sit.int204.actionback.dtos.SimpleEventDTO;
import sit.int204.actionback.dtos.UserDTO;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.repo.UserRepository;
import sit.int204.actionback.utils.ListMapper;

import java.time.Instant;
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

    public List<UserDTO> getEvent(){
        return listMapper.mapList(userRepository.findAllByOrderByNameAsc(), UserDTO.class, modelMapper);
    }

    public ResponseEntity addUser(UserDTO user){

      User user_user =  modelMapper.map(user, User.class);
        userRepository.saveUser(user_user);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
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

    public ResponseEntity editUser(UserDTO editUser , int id ) {

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

    public ResponseEntity getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("THIS ID NOT EXIST: " + id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(user.get(), User.class));
    }

}

