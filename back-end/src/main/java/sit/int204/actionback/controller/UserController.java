package sit.int204.actionback.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.UserAddDTO;
import sit.int204.actionback.dtos.UserModifyDTO;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.service.UserService;
import sit.int204.actionback.utils.ListMapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "*")
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

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PostMapping("")
    public ResponseEntity addUser(@Valid @RequestBody UserAddDTO user) {
        System.out.println("POST MAPPING ADD USER START");
        return userService.addUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id , HttpServletRequest request) {
        userService.deleteUser(id,request);
    }



    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody UserModifyDTO user, @PathVariable int id) {
        System.out.println(user); //id name email role
        return userService.editUser(user, id);
    }

    @GetMapping("/lecturer")
    public ResponseEntity getGay(){
        return ResponseEntity.ok().body("Gay");
    }
}
