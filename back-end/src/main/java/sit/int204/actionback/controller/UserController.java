package sit.int204.actionback.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.UserAddDTO;
import sit.int204.actionback.entities.User;
import sit.int204.actionback.service.UserService;
import sit.int204.actionback.utils.ListMapper;
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
    public void deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
    }



    @PutMapping("/{id}")
    public ResponseEntity update(@Valid @RequestBody UserAddDTO user, @PathVariable int id) {
        return userService.editUser(user, id);
    }

    @GetMapping("/lecturer")
    public ResponseEntity getGay(){
        return ResponseEntity.ok().body("Gay");
    }
}
