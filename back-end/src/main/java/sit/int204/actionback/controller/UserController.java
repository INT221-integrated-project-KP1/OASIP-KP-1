package sit.int204.actionback.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.UserDTO;
import sit.int204.actionback.dtos.UserGetDTO;
import sit.int204.actionback.dtos.UserMatchingDTO;
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
    public List<UserGetDTO> getUser(){
        return userService.getUserAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping("")
    public ResponseEntity addUser(@Valid @RequestBody UserDTO user){
        userService.addUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @DeleteMapping("/{id}")
    public void deleteTest(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @GetMapping("/matching")
    public ResponseEntity matchPassword(@Valid @RequestBody UserMatchingDTO user) {
        return userService.matchPassword(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody UserGetDTO user, @PathVariable int id) {
        return userService.editUser(user, id);
    }
}
