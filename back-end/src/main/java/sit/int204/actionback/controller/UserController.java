package sit.int204.actionback.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.repo.UserRepository;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public List<User> getEvent(){
        return userRepository.findAll();
    }

    @PostMapping("")
    public ResponseEntity addUser(@RequestBody User user){
        userRepository.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }
}
