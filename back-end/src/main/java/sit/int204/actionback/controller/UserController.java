package sit.int204.actionback.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.UserDTO;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.service.UserService;
import sit.int204.actionback.utils.ListMapper;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    @GetMapping("")
    public List<User> getUser(){
        return userService.getEvent();
    }


    @PostMapping("")
    public ResponseEntity addUser(@Valid @RequestBody UserDTO user){
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody UserDTO user, @PathVariable int id) {
        return userService.editUser(user, id);
    }
}
