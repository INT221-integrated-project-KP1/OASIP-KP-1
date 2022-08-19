package sit.int204.actionback.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import sit.int204.actionback.dtos.EventPageDTO;
import sit.int204.actionback.dtos.UserDTO;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.repo.UserRepository;
import sit.int204.actionback.utils.ListMapper;

import java.util.List;

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

}

