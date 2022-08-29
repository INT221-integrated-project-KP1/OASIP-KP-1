package sit.int204.actionback.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int204.actionback.dtos.UserMatchingDTO;
import sit.int204.actionback.service.MatchingService;
import sit.int204.actionback.utils.ListMapper;

import javax.validation.Valid;


@RestController
@RequestMapping("api/matching")
@CrossOrigin(origins = "*")

public class MatchingController {
    @Autowired
    private MatchingService matchingService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ListMapper listMapper;

    @PostMapping("/matching")
    public ResponseEntity matchPassword(@Valid @RequestBody UserMatchingDTO user) {
        return matchingService.matchPassword(user);
    }
}